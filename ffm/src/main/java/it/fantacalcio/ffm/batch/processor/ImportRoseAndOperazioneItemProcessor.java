package it.fantacalcio.ffm.batch.processor;

import it.fantacalcio.ffm.batch.model.RosaBatchRecord;
import it.fantacalcio.ffm.converter.OperazioneConverter;
import it.fantacalcio.ffm.domain.dto.*;
import it.fantacalcio.ffm.domain.entity.Operazione;
import it.fantacalcio.ffm.facade.ApiGatewayFacade;
import it.fantacalcio.ffm.utility.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDateTime;

import static it.fantacalcio.ffm.utility.Constants.ANNI_CONTRATTO_DEFAULT;
import static it.fantacalcio.ffm.utility.Constants.TipoOperazioneEnum.ACQUISTO;

@RequiredArgsConstructor
public class ImportRoseAndOperazioneItemProcessor implements ItemProcessor<RosaBatchRecord, Operazione> {

    private final ApiGatewayFacade apiGatewayFacade;
    private final Constants.SessioneMercatoOpAcquistoEnum sessioneMercatoOpAcquistoEnum;

    @Override
    public Operazione process(RosaBatchRecord item) throws Exception {
        if ("$".equals(item.getSquadraNazioneCategoria())) {
            return null; // Filtra le righe di separazione
        }
        SquadraDto squadraDto = apiGatewayFacade.squadraFromJoinedString(item.getSquadraNazioneCategoria());
        TipoOperazioneDto tipoOperazioneDto = apiGatewayFacade.getTipoOperazioneBySigla(ACQUISTO.getSigla());
        StagioneDto stagioneDto = apiGatewayFacade.getLastStagione();
        if(squadraDto != null) {
            squadraDto = apiGatewayFacade.getSquadraByNomeOrSave(squadraDto);
            GiocatoreDto giocatoreDto = apiGatewayFacade.getGiocatoreByIdFantagazzetta(Integer.valueOf(item.getIdFantagazzetta()));
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
                    Integer.valueOf(item.getCostoAcquisto()),
                    Constants.SegnoEnum.DEBITO.getSigla()
            );
            AcquistoDto acquistoDto = new AcquistoDto(null,
                    operazioneDto,
                    ANNI_CONTRATTO_DEFAULT);
            operazioneDto.setTransazione(transazioneOperazioneDto);
            operazioneDto.setAcquisto(acquistoDto);
            return OperazioneConverter.toEntity(operazioneDto);
        }else{
            return null;
        }
    }
}
