package it.fantacalcio.ffm.batch.processor;

import it.fantacalcio.ffm.batch.model.TrattativaScambioBatch;
import it.fantacalcio.ffm.domain.dto.GiocatoreDto;
import it.fantacalcio.ffm.domain.dto.SquadraDto;
import it.fantacalcio.ffm.domain.dto.TipoDettTrattativaDto;
import it.fantacalcio.ffm.domain.dto.TipoOperazioneDto;
import it.fantacalcio.ffm.domain.model.GiocatoreTrattativaScambioComposite;
import it.fantacalcio.ffm.domain.model.fantaleghe.FantalegheTrattativeScambio;
import it.fantacalcio.ffm.facade.ApiGatewayFacade;
import it.fantacalcio.ffm.utility.CollectionUtility;
import it.fantacalcio.ffm.utility.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class ImportTrattativeScambioWebApiItemProcessor implements ItemProcessor<FantalegheTrattativeScambio.Scambio, TrattativaScambioBatch> {

    private final ApiGatewayFacade apiGatewayFacade;
    private final Constants.SessioneMercatoTrattiveScambioEnum sessioneMercatoTrattiveScambioEnum;

    @Override
    public TrattativaScambioBatch process(FantalegheTrattativeScambio.Scambio item) throws Exception {
        SquadraDto squadraDtoA = apiGatewayFacade.getSquadraByIdFantagazzetta(item.getIdSquadraA());
        SquadraDto squadraDtoB = apiGatewayFacade.getSquadraByIdFantagazzetta(item.getIdSquadraB());
        Map<Constants.SquadraOwnerEnum, Integer> mapSquadraCreditiPagati = new HashMap<>();
        Constants.SquadraOwnerEnum squadraPagante = null;
        Integer creditiPagati = null;
        List<GiocatoreTrattativaScambioComposite> listaGiocatoriScambiati = new ArrayList<>();
        if(item.getCreditiPagatiSquadraA() != null && item.getCreditiPagatiSquadraA() > 0){
            squadraPagante = Constants.SquadraOwnerEnum.SQUADRA_A;
            creditiPagati = item.getCreditiPagatiSquadraA();
        }else if(item.getCreditiPagatiSquadraB() != null && item.getCreditiPagatiSquadraB() > 0){
            squadraPagante = Constants.SquadraOwnerEnum.SQUADRA_B;
            creditiPagati = item.getCreditiPagatiSquadraB();
        }
        if(creditiPagati != null){
            mapSquadraCreditiPagati.put(squadraPagante, creditiPagati);
        }
        CollectionUtility.safeForEach(item.getListGiocatoriCedutiSquadraA(), idGiocatoreTrattativaScambio -> {
            listaGiocatoriScambiati.add(creaGiocatoreTrattativaScambioComposite(idGiocatoreTrattativaScambio, Constants.SquadraOwnerEnum.SQUADRA_A));
        });
        CollectionUtility.safeForEach(item.getListGiocatoriCedutiSquadraB(), idGiocatoreTrattativaScambio -> {
            listaGiocatoriScambiati.add(creaGiocatoreTrattativaScambioComposite(idGiocatoreTrattativaScambio, Constants.SquadraOwnerEnum.SQUADRA_B));
        });
        return new TrattativaScambioBatch(squadraDtoA,squadraDtoB,sessioneMercatoTrattiveScambioEnum,mapSquadraCreditiPagati,listaGiocatoriScambiati);
    }

    private GiocatoreTrattativaScambioComposite creaGiocatoreTrattativaScambioComposite(Integer idGiocatoreTrattativaScambio, Constants.SquadraOwnerEnum squadraOwner){
        Constants.SquadraOwnerEnum squadraAcquirente = squadraOwner.equals(Constants.SquadraOwnerEnum.SQUADRA_A) ? Constants.SquadraOwnerEnum.SQUADRA_B : Constants.SquadraOwnerEnum.SQUADRA_A;
        GiocatoreDto giocatoreDto = apiGatewayFacade.getGiocatoreByIdFantagazzetta(idGiocatoreTrattativaScambio);
        TipoDettTrattativaDto tipoDettTrattativaDto = apiGatewayFacade.getTipoDettTrattativaBySigla(Constants.TipoDettTrattativaEnum.DEFINITIVO.getSigla());
        Map<Constants.SquadraOwnerEnum, TipoOperazioneDto> squadraTipoOperazioneMap = new HashMap<>();
        TipoOperazioneDto tipoOperazioneDtoAcquisto = apiGatewayFacade.getTipoOperazioneBySigla(Constants.TipoOperazioneEnum.ACQUISTO.getSigla());
        TipoOperazioneDto tipoOperazioneDtoCessione = apiGatewayFacade.getTipoOperazioneBySigla(Constants.TipoOperazioneEnum.CESSIONE.getSigla());
        squadraTipoOperazioneMap.put(squadraOwner,tipoOperazioneDtoCessione);
        squadraTipoOperazioneMap.put(squadraAcquirente,tipoOperazioneDtoAcquisto);
        return new GiocatoreTrattativaScambioComposite(giocatoreDto, tipoDettTrattativaDto, squadraTipoOperazioneMap);
     }
}
