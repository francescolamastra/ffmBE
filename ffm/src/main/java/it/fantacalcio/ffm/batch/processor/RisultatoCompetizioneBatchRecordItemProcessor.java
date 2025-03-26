package it.fantacalcio.ffm.batch.processor;

import it.fantacalcio.ffm.batch.model.EsitoRisultato;
import it.fantacalcio.ffm.batch.model.RisultatoCompetizioneBatchRecord;
import it.fantacalcio.ffm.batch.utility.RisultatoCompetizioneHelper;
import it.fantacalcio.ffm.domain.dto.FaseCompetizioneDto;
import it.fantacalcio.ffm.domain.dto.RisultatoCompetizioneDto;
import it.fantacalcio.ffm.domain.dto.SquadraDto;
import it.fantacalcio.ffm.domain.dto.StagioneCompetizioneDto;
import it.fantacalcio.ffm.facade.ApiGatewayFacade;
import it.fantacalcio.ffm.utility.Constants;
import org.springframework.batch.item.ItemProcessor;

import java.util.Arrays;
import java.util.List;

public class RisultatoCompetizioneBatchRecordItemProcessor implements ItemProcessor<RisultatoCompetizioneBatchRecord, List<RisultatoCompetizioneDto>> {

    private final ApiGatewayFacade apiGatewayFacade;
    private final StagioneCompetizioneDto stagioneCompetizioneDto;
    private final FaseCompetizioneDto faseCompetizioneDto;

    public RisultatoCompetizioneBatchRecordItemProcessor(ApiGatewayFacade apiGatewayFacade, StagioneCompetizioneDto stagioneCompetizioneDto, FaseCompetizioneDto faseCompetizioneDto) {
        this.apiGatewayFacade = apiGatewayFacade;
        this.stagioneCompetizioneDto = stagioneCompetizioneDto;
        this.faseCompetizioneDto = faseCompetizioneDto;
    }

    @Override
    public List<RisultatoCompetizioneDto> process(RisultatoCompetizioneBatchRecord risultatoCompetizioneBatchRecord) {
        if (apiGatewayFacade.existsRisultatoCompetizioneByStagioneCompetizioneAndGiornataSerieA(stagioneCompetizioneDto, risultatoCompetizioneBatchRecord.getGiornataSerieA())) {
            //RISULTATO GIORNATA GIA' INSERITO IN PRECEDENTE RUN
            return null;
        } else {
            return Arrays.asList(
                    createRisultatoCompetizioneDto(risultatoCompetizioneBatchRecord, Constants.SquadraOwnerEnum.SQUADRA_A),
                    createRisultatoCompetizioneDto(risultatoCompetizioneBatchRecord, Constants.SquadraOwnerEnum.SQUADRA_B)
            );
        }
    }

    private RisultatoCompetizioneDto createRisultatoCompetizioneDto(RisultatoCompetizioneBatchRecord item, Constants.SquadraOwnerEnum squadraOwner) {
        boolean isSquadraA = squadraOwner.equals(Constants.SquadraOwnerEnum.SQUADRA_A);
        SquadraDto squadraDto = getSquadraDto(item, isSquadraA);
        EsitoRisultato esitoRisultato = RisultatoCompetizioneHelper.calcolaEsitoRisultato(item.getRisultato());
        String risultato = getRisultato(esitoRisultato, squadraOwner);
        String luogoRisultato = getLuogoRisultato(isSquadraA);
        int bonusPunti = getBonusPunti(esitoRisultato, isSquadraA);

        return new RisultatoCompetizioneDto(null, stagioneCompetizioneDto, squadraDto, risultato, item.getGiornataSerieA(),
                item.getGiornataSerieA(), luogoRisultato, faseCompetizioneDto, bonusPunti);
    }

    private SquadraDto getSquadraDto(RisultatoCompetizioneBatchRecord item, boolean isSquadraA) {
        return apiGatewayFacade.squadraFromJoinedString(isSquadraA ? item.getSquadraA() : item.getSquadraB());
    }

    private String getRisultato(EsitoRisultato esitoRisultato, Constants.SquadraOwnerEnum squadraOwner) {
        if (esitoRisultato.getSquadraVincitrice() == null) {
            return Constants.RisultatoCompetizioneEnum.PAREGGIO.getSigla();
        }
        return esitoRisultato.getSquadraVincitrice().equals(squadraOwner) ? Constants.RisultatoCompetizioneEnum.VITTORIA.getSigla() : Constants.RisultatoCompetizioneEnum.SCONFITTA.getSigla();
    }

    private String getLuogoRisultato(boolean isSquadraA) {
        return isSquadraA ? Constants.StadioCompetizioneEnum.CASA.getSigla() : Constants.StadioCompetizioneEnum.TRASFERTA.getSigla();
    }

    private int getBonusPunti(EsitoRisultato esitoRisultato, boolean isSquadraA) {
        return isSquadraA ? esitoRisultato.getBonusSquadraA() : esitoRisultato.getBonusSquadraB();
    }
}
