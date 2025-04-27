package it.fantacalcio.ffm.batch.processor;

import it.fantacalcio.ffm.batch.model.ListoneBatchRecord;
import it.fantacalcio.ffm.batch.model.GiocatoreListoneGiocatoreComposite;
import it.fantacalcio.ffm.domain.dto.GiocatoreDto;
import it.fantacalcio.ffm.domain.dto.GiocatoreListoneDto;
import it.fantacalcio.ffm.domain.dto.StagioneDto;
import it.fantacalcio.ffm.facade.ApiGatewayFacade;
import it.fantacalcio.ffm.utility.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class ImportListoneItemProcessor implements ItemProcessor<ListoneBatchRecord, GiocatoreListoneGiocatoreComposite> {
    private final ApiGatewayFacade apiGatewayFacade;
    private final Constants.TipologiaListoneEnum tipologiaListoneEnum;

    @Override
    public GiocatoreListoneGiocatoreComposite process(ListoneBatchRecord item) {
        StagioneDto stagioneDto = apiGatewayFacade.getLastStagione();
        GiocatoreListoneDto giocatoreListoneDto = new GiocatoreListoneDto(null, item.idFantagazzetta(), stagioneDto, item.fvm(), tipologiaListoneEnum, LocalDateTime.now());
        GiocatoreDto giocatoreDto = new GiocatoreDto(null, item.idFantagazzetta(), item.nome(), item.ruolo());
        return new GiocatoreListoneGiocatoreComposite(giocatoreListoneDto,giocatoreDto);
    }
}
