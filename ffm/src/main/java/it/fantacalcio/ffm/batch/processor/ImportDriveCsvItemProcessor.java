package it.fantacalcio.ffm.batch.processor;

import it.fantacalcio.ffm.batch.model.DriveCsvRow;
import it.fantacalcio.ffm.batch.model.GiocatoreListoneGiocatoreComposite;
import it.fantacalcio.ffm.batch.model.GiocatoreRosaOperazioneComposite;
import it.fantacalcio.ffm.domain.dto.*;
import it.fantacalcio.ffm.facade.ApiGatewayFacade;
import it.fantacalcio.ffm.utility.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static it.fantacalcio.ffm.utility.Constants.TipoOperazioneEnum.SVINCOLO;

@RequiredArgsConstructor
public class ImportDriveCsvItemProcessor implements ItemProcessor<DriveCsvRow, Object> {
    private final ApiGatewayFacade apiGatewayFacade;
    private final Constants.TipoSheet tipoSheet;
    private final StagioneDto stagioneDto;
    private final SquadraDto squadraDto;
    private final Constants.TipologiaRosaEnum tipologiaRosaEnum;
    private final List<GiocatoreRosaDto> giocatoriRosaIniziale;

    @Override
    public Object process(DriveCsvRow item) {
        switch (tipoSheet) {
            case LISTONE -> {
                GiocatoreListoneDto giocatoreListoneDto = new GiocatoreListoneDto(null, Integer.valueOf(item.getColumn4()), stagioneDto, 0, Constants.TipologiaListoneEnum.INIZIALE, LocalDateTime.now());
                GiocatoreDto giocatoreDto = new GiocatoreDto(null, Integer.valueOf(item.getColumn4()), item.getColumn2(), item.getColumn1());
                return new GiocatoreListoneGiocatoreComposite(giocatoreListoneDto, giocatoreDto);
            }
            case SQUADRA -> {
                return processByTipologiaRosa(item);
            }
            default -> {
                return null;
            }
        }
    }

    private GiocatoreRosaOperazioneComposite processByTipologiaRosa(DriveCsvRow item) {
        return switch (tipologiaRosaEnum) {
            case INIZIALE -> processIniziale(item);
            case POST_LISTONE -> processPostListone(item);
            default -> throw new IllegalArgumentException("Tipologia Rosa non supportata: " + tipologiaRosaEnum);
        };
    }

    private GiocatoreRosaOperazioneComposite processIniziale(DriveCsvRow item) {
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
            Optional<GiocatoreDto> giocatoreDto = apiGatewayFacade.getGiocatoreByNome(item.getColumn2());
            if(giocatoreDto.isPresent()) {
                List<GiocatoreRosaDto> listGiocatoriRosaAttuale = new ArrayList<>();
                listGiocatoriRosaAttuale.add(createGiocatoreRosaDto(giocatoreDto.get(), Integer.valueOf(item.getColumn6()), Integer.valueOf(item.getColumn8())));
                return new GiocatoreRosaOperazioneComposite(listGiocatoriRosaAttuale, Collections.emptyList());
            }else{
                return null;
            }
        }
    }

    private GiocatoreRosaOperazioneComposite processPostListone(DriveCsvRow item) {
        List<OperazioneDto> operazioneDtoList = new ArrayList<>();
        List<GiocatoreRosaDto> listGiocatoriRosaAttuale = new ArrayList<>();
        Optional<GiocatoreDto> giocatoreDto = apiGatewayFacade.getGiocatoreByNome(item.getColumn2());
        if (giocatoreDto.isPresent()) {
            if(item.getColumn5().equalsIgnoreCase("Svincolato")){
                TipoOperazioneDto tipoOperazioneDto = apiGatewayFacade.getTipoOperazioneBySigla(SVINCOLO.getSigla());
                GiocatoreRosaDto giocatoreRosaDto = giocatoriRosaIniziale.stream().filter(giocatoreRosaIniziale -> giocatoreRosaIniziale.getIdGiocatore().nome().equalsIgnoreCase(item.getColumn2())).findFirst().get();
                operazioneDtoList.add(createOperazioneDto(giocatoreRosaDto.getIdGiocatore(), tipoOperazioneDto,Constants.SessioneMercatoOpAcquistoEnum.INIZIALE, Constants.SegnoEnum.CREDITO, Integer.parseInt(item.getColumn4()), Constants.PERCENTUALE_SVINCOLO_100, true));
            }else {
                listGiocatoriRosaAttuale.add(createGiocatoreRosaDto(giocatoreDto.get(), Integer.valueOf(item.getColumn4()), 0));
            }
        } else {
            return null;
        }
        return new GiocatoreRosaOperazioneComposite(listGiocatoriRosaAttuale, operazioneDtoList);
    }

    private GiocatoreRosaDto createGiocatoreRosaDto(GiocatoreDto giocatoreDto, Integer costo, Integer anniContratto) {
        GiocatoreRosaDto giocatoreRosaDto = new GiocatoreRosaDto();
        giocatoreRosaDto.setIdSquadra(squadraDto);
        giocatoreRosaDto.setIdGiocatore(giocatoreDto);
        giocatoreRosaDto.setIdStagione(stagioneDto);
        giocatoreRosaDto.setTipologiaRosa(tipologiaRosaEnum);
        giocatoreRosaDto.setCostoAcquisto(costo);
        giocatoreRosaDto.setFvm(0);
        giocatoreRosaDto.setAnniContratto(anniContratto);
        return giocatoreRosaDto;
    }

    private OperazioneDto createOperazioneDto(GiocatoreDto giocatoreDto,
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
}
