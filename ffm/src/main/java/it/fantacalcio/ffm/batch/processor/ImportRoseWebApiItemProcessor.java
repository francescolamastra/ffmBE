package it.fantacalcio.ffm.batch.processor;

import it.fantacalcio.ffm.batch.model.GiocatoreRosaOperazioneComposite;
import it.fantacalcio.ffm.domain.dto.*;
import it.fantacalcio.ffm.domain.model.fantaleghe.FantalegheTeam;
import it.fantacalcio.ffm.facade.ApiGatewayFacade;
import it.fantacalcio.ffm.utility.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDateTime;
import java.util.*;

import static it.fantacalcio.ffm.utility.Constants.ANNI_CONTRATTO_DEFAULT;
import static it.fantacalcio.ffm.utility.Constants.SEMICOLON_SEPARATOR;
import static it.fantacalcio.ffm.utility.Constants.TipoOperazioneEnum.SVINCOLO;

@RequiredArgsConstructor
public class ImportRoseWebApiItemProcessor implements ItemProcessor<FantalegheTeam, GiocatoreRosaOperazioneComposite> {

    private final ApiGatewayFacade apiGatewayFacade;
    private final Constants.TipologiaRosaEnum tipologiaRosaEnum;

    @Override
    public GiocatoreRosaOperazioneComposite process(FantalegheTeam item) throws Exception {
        if (item.getJoinedIdCalciatori().isBlank()) {
            return null;
        }

        List<Integer> calciatoriIds = parseIds(item.getJoinedIdCalciatori());
        List<Integer> calciatoriCost = parseIds(item.getJoinedCostoCalciatori());
        validateLists(calciatoriIds, calciatoriCost);

        SquadraDto squadraDto = getOrCreateSquadra(item);
        if (squadraDto == null) {
            return null;
        }

        StagioneDto stagioneDto = apiGatewayFacade.getLastStagione();
        return processByTipologiaRosa(calciatoriIds, calciatoriCost, squadraDto, stagioneDto);
    }

    private List<Integer> parseIds(String joinedIds) {
        return Arrays.stream(joinedIds.split(SEMICOLON_SEPARATOR)).map(Integer::parseInt).toList();
    }

    private void validateLists(List<Integer> ids, List<Integer> costs) throws Exception {
        if (ids.size() != costs.size()) {
            throw new Exception("La lista calciatori e relativa lista costo acquisto non sono congruenti!");
        }
    }

    private SquadraDto getOrCreateSquadra(FantalegheTeam item) {
        SquadraDto squadraDto = apiGatewayFacade.squadraFromJoinedString(item.getNomeTeam());
        if (squadraDto != null) {
            squadraDto.setIdFantagazzetta(item.getIdTeam());
            return apiGatewayFacade.getSquadraByNomeOrSave(squadraDto);
        }
        return null;
    }

    private GiocatoreRosaOperazioneComposite processByTipologiaRosa(List<Integer> calciatoriIds, List<Integer> calciatoriCost, SquadraDto squadraDto, StagioneDto stagioneDto) {
        return switch (tipologiaRosaEnum) {
            case INIZIALE -> processIniziale(calciatoriIds, calciatoriCost, squadraDto, stagioneDto);
            case POST_LISTONE -> processPostListone(calciatoriIds, calciatoriCost, squadraDto, stagioneDto);
            case STIPENDI_SETTEMBRE, STIPENDI_FEBBRAIO, FINALE ->
                    processDefault(calciatoriIds, calciatoriCost, squadraDto, stagioneDto);
            case MANAGERIALE -> processManageriale(calciatoriIds, calciatoriCost, squadraDto, stagioneDto);
            default -> throw new IllegalArgumentException("Tipologia Rosa non supportata: " + tipologiaRosaEnum);
        };
    }

    private GiocatoreRosaOperazioneComposite processManageriale(List<Integer> calciatoriIds, List<Integer> calciatoriCost, SquadraDto squadraDto, StagioneDto stagioneDto) {
        TipoOperazioneDto tipoOperazioneDto = apiGatewayFacade.getTipoOperazioneBySigla(Constants.TipoOperazioneEnum.ACQUISTO.getSigla());
        List<GiocatoreRosaDto> listGiocatoriRosaAttuale = new ArrayList<>();
        List<OperazioneDto> operazioneDtoList = new ArrayList<>();
        for (int i = 0; i < calciatoriIds.size(); i++) {
            GiocatoreDto giocatoreDto = apiGatewayFacade.getGiocatoreByIdFantagazzetta(calciatoriIds.get(i));
            boolean existsAcquistoStagionale = apiGatewayFacade.existsOperazioneByIdStagioneAndIdSquadraAndIdGiocatoreAndIdTipoOperazioneAndSessioneMercatoIn(stagioneDto, squadraDto, giocatoreDto, tipoOperazioneDto, List.of(Constants.SessioneMercatoOpAcquistoEnum.PREASTA, Constants.SessioneMercatoOpAcquistoEnum.AGOSTO, Constants.SessioneMercatoOpAcquistoEnum.SETTEMBRE, Constants.SessioneMercatoOpAcquistoEnum.FEBBRAIO));
            int anniContratto = calculateAnniContratto(existsAcquistoStagionale, stagioneDto);
            if(anniContratto > 0){
                listGiocatoriRosaAttuale.add(createGiocatoreRosaDto(calciatoriIds.get(i), calciatoriCost.get(i), anniContratto, squadraDto, stagioneDto, Constants.TipologiaListoneEnum.INIZIALE));
            }else{
                operazioneDtoList.add(createOperazioneDto(squadraDto, giocatoreDto, stagioneDto, tipoOperazioneDto, Constants.SessioneMercatoOpAcquistoEnum.FINALE, Constants.SegnoEnum.CREDITO, 0, 0,false));
            }
        }
        return new GiocatoreRosaOperazioneComposite(listGiocatoriRosaAttuale, operazioneDtoList);
    }

    private GiocatoreRosaOperazioneComposite processIniziale(List<Integer> calciatoriIds, List<Integer> calciatoriCost, SquadraDto squadraDto, StagioneDto stagioneDto) {
        if (apiGatewayFacade.existsGiocatoreRosaByStagioneAndSquadraAndTipologiaRosa(stagioneDto, squadraDto, Constants.TipologiaRosaEnum.PREASTA)) {
            return null;
        }

        Optional<StagioneDto> stagionePrecedente = apiGatewayFacade.getStagioneByAnnoFine(stagioneDto.getAnnoFine() - 1);
        if (stagionePrecedente.isPresent()) {
            List<GiocatoreRosaDto> giocatoriAnnoPrecedente = apiGatewayFacade.getAllGiocatoreRosaByIdStagioneAndIdSquadraAndTipologiaRosa(
                    stagionePrecedente.get(), squadraDto, Constants.TipologiaRosaEnum.MANAGERIALE);
            List<GiocatoreRosaDto> listGiocatoriRosaAttuale = giocatoriAnnoPrecedente.stream()
                    .peek(g -> {
                        g.setId(null);
                        g.setAnniContratto(g.getAnniContratto() - 1);
                    }).toList();
            return new GiocatoreRosaOperazioneComposite(listGiocatoriRosaAttuale, Collections.emptyList());
        }else{
            List<GiocatoreRosaDto> listGiocatoriRosaAttuale = new ArrayList<>();
            for (int i = 0; i < calciatoriIds.size(); i++) {
                listGiocatoriRosaAttuale.add(createGiocatoreRosaDto(calciatoriIds.get(i), calciatoriCost.get(i), ANNI_CONTRATTO_DEFAULT, squadraDto, stagioneDto, Constants.TipologiaListoneEnum.INIZIALE));
            }
            return new GiocatoreRosaOperazioneComposite(listGiocatoriRosaAttuale, Collections.emptyList());
        }
    }

    private GiocatoreRosaOperazioneComposite processPostListone(List<Integer> calciatoriIds, List<Integer> calciatoriCost, SquadraDto squadraDto, StagioneDto stagioneDto) {
        List<OperazioneDto> operazioneDtoList = new ArrayList<>();
        List<GiocatoreRosaDto> giocatoriRosaIniziale = apiGatewayFacade.getAllGiocatoreRosaByIdStagioneAndIdSquadraAndTipologiaRosa(
                stagioneDto, squadraDto, Constants.TipologiaRosaEnum.INIZIALE);

        List<GiocatoreRosaDto> listGiocatoriRosaAttuale = new ArrayList<>();
        for (int i = 0; i < calciatoriIds.size(); i++) {
            GiocatoreRosaDto giocatoreRosaDto = createGiocatoreRosaDto(calciatoriIds.get(i), calciatoriCost.get(i), 0, squadraDto, stagioneDto, Constants.TipologiaListoneEnum.INIZIALE);
            listGiocatoriRosaAttuale.add(giocatoreRosaDto);
        }
        List<GiocatoreRosaDto> giocatoriDaSvincolare = new ArrayList<>();

        giocatoriRosaIniziale.forEach(giocatoreRosaIniziale -> {
            boolean isPresente = listGiocatoriRosaAttuale.stream()
                    .anyMatch(giocatoreRosaAttuale ->
                            giocatoreRosaIniziale.getIdGiocatore().idFantagazzetta().equals(
                                    giocatoreRosaAttuale.getIdGiocatore().idFantagazzetta()
                            )
                    );
            if (isPresente) {
                giocatoriDaSvincolare.add(giocatoreRosaIniziale);
            }
        });
        if(!giocatoriDaSvincolare.isEmpty()) {
            TipoOperazioneDto tipoOperazioneDto = apiGatewayFacade.getTipoOperazioneBySigla(SVINCOLO.getSigla());
            for (GiocatoreRosaDto giocatoreRosaDto : giocatoriDaSvincolare) {
                GiocatoreDto giocatoreDto = apiGatewayFacade.getGiocatoreByIdFantagazzetta(giocatoreRosaDto.getIdGiocatore().idFantagazzetta());
                int costo = stagioneDto.getAnnoInizio().equals(2025) ? giocatoreRosaDto.getCostoAcquisto() : giocatoreRosaDto.getFvm();
                operazioneDtoList.add(createOperazioneDto(squadraDto,giocatoreDto,stagioneDto,tipoOperazioneDto,Constants.SessioneMercatoOpAcquistoEnum.INIZIALE, Constants.SegnoEnum.CREDITO, costo, Constants.PERCENTUALE_SVINCOLO_100, true));
            }
        }
        return new GiocatoreRosaOperazioneComposite(listGiocatoriRosaAttuale, operazioneDtoList);
    }

    private GiocatoreRosaOperazioneComposite processDefault(List<Integer> calciatoriIds, List<Integer> calciatoriCost, SquadraDto squadraDto, StagioneDto stagioneDto) {
        List<GiocatoreRosaDto> listGiocatoriRosaAttuale = new ArrayList<>();
        for (int i = 0; i < calciatoriIds.size(); i++) {
            GiocatoreRosaDto giocatoreRosaDto = createGiocatoreRosaDto(calciatoriIds.get(i), calciatoriCost.get(i), 0, squadraDto, stagioneDto, Constants.TipologiaListoneEnum.STIPENDI);
            listGiocatoriRosaAttuale.add(giocatoreRosaDto);
        }
        return new GiocatoreRosaOperazioneComposite(listGiocatoriRosaAttuale, Collections.emptyList());
    }

    private GiocatoreRosaDto createGiocatoreRosaDto(Integer idCalciatore, Integer costoAcquisto, Integer anniContratto, SquadraDto squadraDto, StagioneDto stagioneDto, Constants.TipologiaListoneEnum tipologiaListone) {
        GiocatoreListoneDto giocatoreListoneDto = apiGatewayFacade.getGiocatoreListoneByIdStagioneAndIdFantagazzettaAndTipologiaListone(stagioneDto, idCalciatore, tipologiaListone);
        GiocatoreDto giocatoreDto = apiGatewayFacade.getGiocatoreByIdFantagazzetta(idCalciatore);

        GiocatoreRosaDto giocatoreRosaDto = new GiocatoreRosaDto();
        giocatoreRosaDto.setIdSquadra(squadraDto);
        giocatoreRosaDto.setIdGiocatore(giocatoreDto);
        giocatoreRosaDto.setIdStagione(stagioneDto);
        giocatoreRosaDto.setTipologiaRosa(tipologiaRosaEnum);
        giocatoreRosaDto.setCostoAcquisto(costoAcquisto);
        giocatoreRosaDto.setFvm(giocatoreListoneDto.fvm());
        giocatoreRosaDto.setAnniContratto(anniContratto);
        return giocatoreRosaDto;
    }

    private OperazioneDto createOperazioneDto(SquadraDto squadraDto, GiocatoreDto giocatoreDto, StagioneDto stagioneDto,
                                              TipoOperazioneDto tipoOperazioneDto,
                                              Constants.SessioneMercatoOpAcquistoEnum sessioneMercato, Constants.SegnoEnum segnoOperazione, int valoreTransazione, int percentualeSvincolo, boolean prelazionabile) {
        OperazioneDto operazioneDto = new OperazioneDto();
        operazioneDto.setIdSquadra(squadraDto);
        operazioneDto.setIdGiocatore(giocatoreDto);
        operazioneDto.setIdStagione(stagioneDto);
        operazioneDto.setIdTipoOperazione(tipoOperazioneDto);
        operazioneDto.setDataCreazione(LocalDateTime.now());
        operazioneDto.setSessioneMercato(sessioneMercato);

        TransazioneOperazioneDto transazioneOperazioneDto = new TransazioneOperazioneDto(
                null, operazioneDto, valoreTransazione, segnoOperazione.getSigla());

        SvincoloDto svincoloDto = new SvincoloDto(null,
                operazioneDto,
                percentualeSvincolo,
                prelazionabile
        );
        operazioneDto.setTransazione(transazioneOperazioneDto);
        operazioneDto.setSvincolo(svincoloDto);
        return operazioneDto;
    }

    private int calculateAnniContratto(boolean existsAcquistoStagionale, StagioneDto stagioneDto) {
        int anniContratto = 0;
        if(existsAcquistoStagionale){
            anniContratto = ANNI_CONTRATTO_DEFAULT;
        }else{
            Optional<StagioneDto> stagionePrecedente = apiGatewayFacade.getStagioneByAnnoFine(stagioneDto.getAnnoFine() - 1);
            if (stagionePrecedente.isPresent()) {
                Optional<GiocatoreRosaDto> giocatoreRosaDtoManagerialeAnnoPrecedente = apiGatewayFacade.findByIdStagioneAndTipologiaRosa(stagionePrecedente.get(), Constants.TipologiaRosaEnum.MANAGERIALE);
                if(giocatoreRosaDtoManagerialeAnnoPrecedente.isPresent()){
                    anniContratto = giocatoreRosaDtoManagerialeAnnoPrecedente.get().getAnniContratto()-1;
                }
            }
        }
        return anniContratto;
    }

}
