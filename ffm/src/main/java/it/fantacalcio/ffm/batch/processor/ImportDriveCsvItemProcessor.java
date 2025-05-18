package it.fantacalcio.ffm.batch.processor;

import it.fantacalcio.ffm.batch.model.DriveCsvRow;
import it.fantacalcio.ffm.batch.model.GiocatoreListoneGiocatoreComposite;
import it.fantacalcio.ffm.batch.model.GiocatoreRosaOperazioneComposite;
import it.fantacalcio.ffm.domain.dto.*;
import it.fantacalcio.ffm.domain.model.GiocatoreTrattativaScambio;
import it.fantacalcio.ffm.domain.model.TrattativaScambio;
import it.fantacalcio.ffm.facade.ApiGatewayFacade;
import it.fantacalcio.ffm.utility.CollectionUtility;
import it.fantacalcio.ffm.utility.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static it.fantacalcio.ffm.utility.Constants.TipoOperazioneEnum.ACQUISTO;
import static it.fantacalcio.ffm.utility.Constants.TipoOperazioneEnum.SVINCOLO;

@RequiredArgsConstructor
public class ImportDriveCsvItemProcessor implements ItemProcessor<DriveCsvRow, Object> {
    private final ApiGatewayFacade apiGatewayFacade;
    private final Constants.TipoSheet tipoSheet;
    private final StagioneDto stagioneDto;
    private final SquadraDto squadraDto;
    private final Constants.TipologiaRosaEnum tipologiaRosaEnum;
    private final NazioneDto nazioneDto;

    private final List<DriveCsvRow> buffer = new ArrayList<>();

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
            case SCAMBI -> {
                //scarta le righe di vuote o di separazione drive
                if(buffer.isEmpty() && (item.getColumn1().isBlank() || item.getColumn1().equalsIgnoreCase("Squadra A"))) return null;
                buffer.add(item);
                // Elaborare solo quando si hanno 6 righe
                if (buffer.size() == 6) {
                    TrattativaScambio trattativaScambio = processTrattativaScambio(buffer);
                    buffer.clear(); // Svuota il buffer per la prossima trattativa
                    return trattativaScambio;
                } else {
                    return null; // Non elaborare finché non si hanno tutte le righe
                }
            }
            default -> {
                return null;
            }
        }
    }

    private GiocatoreRosaOperazioneComposite processByTipologiaRosa(DriveCsvRow item) {
        return switch (tipologiaRosaEnum) {
            case PREASTA -> processPreasta(item);
            case INIZIALE -> processIniziale(item);
            case POST_LISTONE -> processPostListone(item);
            default -> throw new IllegalArgumentException("Tipologia Rosa non supportata: " + tipologiaRosaEnum);
        };
    }

    private GiocatoreRosaOperazioneComposite processPreasta(DriveCsvRow item) {

        Optional<GiocatoreDto> giocatoreDto = apiGatewayFacade.getGiocatoreByNome(item.getColumn2());
        if(giocatoreDto.isPresent() && !apiGatewayFacade.existsGiocatoreRosaByStagioneAndSquadraAndTipologiaRosaAndGiocatore(stagioneDto, squadraDto, Constants.TipologiaRosaEnum.PREASTA, giocatoreDto.get())) {
            List<GiocatoreRosaDto> listGiocatoriRosaAttuale = new ArrayList<>();
            List<OperazioneDto> operazioneDtoList = new ArrayList<>();
            TipoOperazioneDto tipoOperazioneDto = apiGatewayFacade.getTipoOperazioneBySigla(ACQUISTO.getSigla());
            int costo = Integer.valueOf(item.getColumn4());
            listGiocatoriRosaAttuale.add(createGiocatoreRosaDto(giocatoreDto.get(), costo, Constants.ANNI_CONTRATTO_DEFAULT));
            operazioneDtoList.add(createOperazioneDtoAcquisto(giocatoreDto.get(), tipoOperazioneDto, Constants.SessioneMercatoOpAcquistoEnum.PREASTA, Constants.SegnoEnum.DEBITO, costo, Constants.ANNI_CONTRATTO_DEFAULT));
            return new GiocatoreRosaOperazioneComposite(listGiocatoriRosaAttuale, operazioneDtoList);
        }else{
            return null;
        }

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
                operazioneDtoList.add(createOperazioneDtoSvincolo(giocatoreDto.get(), tipoOperazioneDto,Constants.SessioneMercatoOpAcquistoEnum.INIZIALE, Constants.SegnoEnum.CREDITO, Integer.parseInt(item.getColumn4()), Constants.PERCENTUALE_SVINCOLO_100, true));
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

    private OperazioneDto createOperazioneDtoSvincolo(GiocatoreDto giocatoreDto,
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

    private OperazioneDto createOperazioneDtoAcquisto(GiocatoreDto giocatoreDto,
                                                      TipoOperazioneDto tipoOperazioneDto,
                                                      Constants.SessioneMercatoOpAcquistoEnum sessioneMercato, Constants.SegnoEnum segnoOperazione, int valoreTransazione, int anniContratto) {
        OperazioneDto operazioneDto = new OperazioneDto();
        operazioneDto.setIdSquadra(squadraDto);
        operazioneDto.setIdGiocatore(giocatoreDto);
        operazioneDto.setIdStagione(stagioneDto);
        operazioneDto.setIdTipoOperazione(tipoOperazioneDto);
        operazioneDto.setDataCreazione(LocalDateTime.now());
        operazioneDto.setSessioneMercato(sessioneMercato);

        TransazioneOperazioneDto transazioneOperazioneDto = new TransazioneOperazioneDto(
                null, operazioneDto, valoreTransazione, segnoOperazione.getSigla());

        AcquistoDto acquistoDto = new AcquistoDto(null,
                operazioneDto,
                anniContratto);
        operazioneDto.setTransazione(transazioneOperazioneDto);
        operazioneDto.setAcquisto(acquistoDto);
        return operazioneDto;
    }

    private TrattativaScambio processTrattativaScambio(List<DriveCsvRow> rows) {

        DriveCsvRow rowA = rows.get(0);
        DriveCsvRow rowB = rows.get(3);

        // Estrarre ID squadre
        String siglaSquadraA = rowA.getColumn1().split("\\d+")[0];
        String siglaSquadraB = rowA.getColumn4().split("\\d+")[0];
        Integer idSquadraA = apiGatewayFacade.getSquadraBySiglaAndNazione(siglaSquadraA, nazioneDto).getId();
        Integer idSquadraB = apiGatewayFacade.getSquadraBySiglaAndNazione(siglaSquadraB, nazioneDto).getId();

        // Estrarre giocatori
        List<GiocatoreTrattativaScambio> listGiocatoriCedutiSquadraA = extractGiocatori(rows.subList(0, 3), Constants.SquadraOwnerEnum.SQUADRA_A);
        List<GiocatoreTrattativaScambio> listGiocatoriCedutiSquadraB = extractGiocatori(rows.subList(3, 6), Constants.SquadraOwnerEnum.SQUADRA_B);

        // Calcolare valori numerici
        int bonusPostSquadraA = parseOrDefault(rowA.getColumn10(), 0);
        int bonusPostSquadraB = parseOrDefault(rowB.getColumn10(), 0);
        int creditiPagatiSquadraA = parseOrDefault(rowA.getColumn11(), 0);
        int creditiPagatiSquadraB = -creditiPagatiSquadraA;
        int creditiPostSquadraA = parseOrDefault(rowA.getColumn9(), 0);
        int creditiPostSquadraB = -creditiPostSquadraA;
        int gettoniSquadraA = parseOrDefault(rowA.getColumn12(), 0);
        int gettoniSquadraB = parseOrDefault(rowB.getColumn12(), 0);

        // Determinare la sessione di mercato
        LocalDateTime dataTrattativa = LocalDate.parse(rowA.getColumn3(), DateTimeFormatter.ofPattern("dd/MM/yyyy")).atStartOfDay();
        Constants.SessioneMercatoTrattiveScambioEnum sessioneMercato = determineSessioneMercato(dataTrattativa.getMonthValue());

        // Concatenare clausole
        String clausole = rows.stream()
                .limit(3)
                .map(DriveCsvRow::getColumn13)
                .filter(Objects::nonNull)
                .reduce("", (a, b) -> a + " " + b).trim();

        // Creare l'oggetto TrattativaScambio
        TrattativaScambio trattativaScambio = new TrattativaScambio();
        trattativaScambio.setIdSquadraA(idSquadraA);
        trattativaScambio.setIdSquadraB(idSquadraB);
        trattativaScambio.setListGiocatoriCedutiSquadraA(listGiocatoriCedutiSquadraA);
        trattativaScambio.setListGiocatoriCedutiSquadraB(listGiocatoriCedutiSquadraB);
        trattativaScambio.setBonusPostSquadraA(bonusPostSquadraA);
        trattativaScambio.setBonusPostSquadraB(bonusPostSquadraB);
        trattativaScambio.setCreditiPagatiSquadraA(creditiPagatiSquadraA);
        trattativaScambio.setCreditiPagatiSquadraB(creditiPagatiSquadraB);
        trattativaScambio.setCreditiPostSquadraA(creditiPostSquadraA);
        trattativaScambio.setCreditiPostSquadraB(creditiPostSquadraB);
        trattativaScambio.setGettoniSquadraA(gettoniSquadraA);
        trattativaScambio.setGettoniSquadraB(gettoniSquadraB);
        trattativaScambio.setDataTrattativa(dataTrattativa);
        trattativaScambio.setClausole(clausole);
        trattativaScambio.setSessioneMercatoTrattiveScambioEnum(sessioneMercato);

        return trattativaScambio;
    }

    private int parseOrDefault(String value, int defaultValue) {
        return (value == null || value.isEmpty()) ? defaultValue : Integer.parseInt(value);
    }

    private Constants.SessioneMercatoTrattiveScambioEnum determineSessioneMercato(int month) {
        return switch (month) {
            case 6, 7 -> Constants.SessioneMercatoTrattiveScambioEnum.ESTIVA;
            case 8 -> Constants.SessioneMercatoTrattiveScambioEnum.AGOSTO;
            case 9 -> Constants.SessioneMercatoTrattiveScambioEnum.SETTEMBRE;
            case 10 -> Constants.SessioneMercatoTrattiveScambioEnum.OTTOBRE;
            case 11 -> Constants.SessioneMercatoTrattiveScambioEnum.NOVEMBRE;
            case 12 -> Constants.SessioneMercatoTrattiveScambioEnum.DICEMBRE;
            case 1 -> Constants.SessioneMercatoTrattiveScambioEnum.GENNAIO;
            case 2 -> Constants.SessioneMercatoTrattiveScambioEnum.FEBBRAIO;
            default -> throw new IllegalArgumentException("Mese non valido: " + month);
        };
    }

    private List<GiocatoreTrattativaScambio> extractGiocatori(List<DriveCsvRow> rows, Constants.SquadraOwnerEnum squadraOwnerEnum) {
        List<GiocatoreTrattativaScambio> giocatori = new ArrayList<>();
        for (DriveCsvRow row : rows) {
            String nomeGiocatore = squadraOwnerEnum.equals(Constants.SquadraOwnerEnum.SQUADRA_A) ? row.getColumn7() : row.getColumn5();
            if (nomeGiocatore != null && !nomeGiocatore.isBlank()) {
                Optional<GiocatoreDto> giocatoreDto = apiGatewayFacade.getGiocatoreByNome(nomeGiocatore);
                giocatoreDto.ifPresent(dto -> {
                    String tipoCessione = calcolaSiglaTipoCessione(squadraOwnerEnum.equals(Constants.SquadraOwnerEnum.SQUADRA_A) ? row.getColumn8() : row.getColumn6());
                    giocatori.add(new GiocatoreTrattativaScambio(dto.idFantagazzetta(), null, tipoCessione));
                }
                );
            }
        }
        return giocatori;
    }

    private String calcolaSiglaTipoCessione(String modalitaCessione){
        if(CollectionUtility.containsIgnoreCase(modalitaCessione,"def")
                || CollectionUtility.containsIgnoreCase(modalitaCessione,"des")){
            return Constants.TipoDettTrattativaEnum.DEFINITIVO.getSigla();
        }
        if (CollectionUtility.containsIgnoreCase(modalitaCessione,"pres")
                || CollectionUtility.containsIgnoreCase(modalitaCessione,"pdr")
                || CollectionUtility.containsIgnoreCase(modalitaCessione,"PS")){
            return Constants.TipoDettTrattativaEnum.PRESTITO.getSigla();
        }
        throw new IllegalArgumentException("modalitaCessione non prevista: "+modalitaCessione);
    }
}
