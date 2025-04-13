package it.fantacalcio.ffm.batch.writer;

import it.fantacalcio.ffm.batch.model.TrattativaScambioBatch;
import it.fantacalcio.ffm.facade.ApiGatewayFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

@RequiredArgsConstructor
public class ImportTrattativeScambioWebApiJpaItemWriter implements ItemWriter<TrattativaScambioBatch> {

    private final ApiGatewayFacade apiGatewayFacade;

    @Override
    public void write(Chunk<? extends TrattativaScambioBatch> chunk) throws Exception {
        chunk.forEach(apiGatewayFacade::createTrattativaScambioBatch);
    }
}
