package it.fantacalcio.ffm.batch.processor;

import it.fantacalcio.ffm.converter.OperazioneConverter;
import it.fantacalcio.ffm.domain.dto.*;
import it.fantacalcio.ffm.domain.entity.Operazione;
import it.fantacalcio.ffm.domain.model.fantaleghe.FantalegheOperazioneMercato;
import it.fantacalcio.ffm.facade.ApiGatewayFacade;
import it.fantacalcio.ffm.utility.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDateTime;

import static it.fantacalcio.ffm.utility.Constants.ANNI_CONTRATTO_DEFAULT;
import static it.fantacalcio.ffm.utility.Constants.PERCENTUALE_SVINCOLO_100;
import static it.fantacalcio.ffm.utility.Constants.TipoOperazioneEnum.ACQUISTO;
import static it.fantacalcio.ffm.utility.Constants.TipoOperazioneEnum.SVINCOLO;

@RequiredArgsConstructor
public class ImportOperazioniMercatoWebApiItemProcessor implements ItemProcessor<FantalegheOperazioneMercato.OperazioneMercato, Operazione> {

    private final ApiGatewayFacade apiGatewayFacade;
    private final Boolean isMercatoAcquisto;
    private final Constants.SessioneMercatoOpAcquistoEnum sessioneMercatoOpAcquistoEnum;

    @Override
    public Operazione process(FantalegheOperazioneMercato.OperazioneMercato item) throws Exception {
        SquadraDto squadraDto = apiGatewayFacade.getSquadraByIdFantagazzetta(item.getIdFantagazzettaSquadra());
        if(squadraDto != null) {
            GiocatoreDto giocatoreDto = apiGatewayFacade.getGiocatoreByIdFantagazzetta(item.getIdFantagazzettaGiocatore());
            TipoOperazioneDto tipoOperazioneDto = apiGatewayFacade.getTipoOperazioneBySigla(isMercatoAcquisto ? ACQUISTO.getSigla() : SVINCOLO.getSigla());
            StagioneDto stagioneDto = apiGatewayFacade.getLastStagione();
            OperazioneDto operazioneDto = new OperazioneDto();
            operazioneDto.setIdSquadra(squadraDto);
            operazioneDto.setIdGiocatore(giocatoreDto);
            operazioneDto.setIdStagione(stagioneDto);
            operazioneDto.setIdTipoOperazione(tipoOperazioneDto);
            operazioneDto.setDataCreazione(LocalDateTime.now());
            operazioneDto.setSessioneMercato(sessioneMercatoOpAcquistoEnum);
            TransazioneOperazioneDto transazioneOperazioneDto = new TransazioneOperazioneDto(
                    null,
                    operazioneDto,
                    item.getCosto(),
                    isMercatoAcquisto ? Constants.SegnoEnum.DEBITO.getSigla() : Constants.SegnoEnum.CREDITO.getSigla()
            );
            operazioneDto.setTransazione(transazioneOperazioneDto);
            if(isMercatoAcquisto){
                AcquistoDto acquistoDto = new AcquistoDto(null,
                        operazioneDto,
                        ANNI_CONTRATTO_DEFAULT);
                operazioneDto.setAcquisto(acquistoDto);
            }else {
               SvincoloDto svincoloDto = new SvincoloDto(
                       null,
                       operazioneDto,
                       PERCENTUALE_SVINCOLO_100,
                       false
               );
               operazioneDto.setSvincolo(svincoloDto);
            }
            return OperazioneConverter.toEntity(operazioneDto);
        }else{
            System.out.println("squadra non trovata per idFantagazzetta: "+item.getIdFantagazzettaSquadra());
            return null;
        }
    }
}
