package it.fantacalcio.ffm.batch.processor;

import it.fantacalcio.ffm.batch.model.ListoneBatchRecord;
import it.fantacalcio.ffm.batch.model.ListoneGiocatoreDtoWrapper;
import it.fantacalcio.ffm.domain.dto.GiocatoreDto;
import it.fantacalcio.ffm.domain.dto.ListoneDto;
import it.fantacalcio.ffm.domain.dto.StagioneDto;
import it.fantacalcio.ffm.facade.ApiGatewayFacade;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ExcelItemProcessor implements ItemProcessor<ListoneBatchRecord, ListoneGiocatoreDtoWrapper> {
    private final ApiGatewayFacade apiGatewayFacade;

    public ExcelItemProcessor(ApiGatewayFacade apiGatewayFacade){
        this.apiGatewayFacade = apiGatewayFacade;
    }

    @Override
    public ListoneGiocatoreDtoWrapper process(ListoneBatchRecord item) {
        StagioneDto stagioneDto = apiGatewayFacade.getLastStagione();
        ListoneDto listoneDto = new ListoneDto(null, item.idFantagazzetta(), stagioneDto, item.fvm(), LocalDateTime.now());
        GiocatoreDto giocatoreDto = new GiocatoreDto(null, item.idFantagazzetta(), item.nome(), item.ruolo());
        return new ListoneGiocatoreDtoWrapper(listoneDto,giocatoreDto);
    }
}
