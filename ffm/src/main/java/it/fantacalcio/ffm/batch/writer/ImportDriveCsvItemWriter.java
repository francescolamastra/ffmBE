package it.fantacalcio.ffm.batch.writer;

import it.fantacalcio.ffm.batch.model.GiocatoreListoneGiocatoreComposite;
import it.fantacalcio.ffm.batch.model.GiocatoreRosaOperazioneComposite;
import it.fantacalcio.ffm.domain.model.TrattativaScambio;
import it.fantacalcio.ffm.facade.ApiGatewayFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

@RequiredArgsConstructor
public class ImportDriveCsvItemWriter implements ItemWriter<Object> {

    private final ItemWriter<GiocatoreRosaOperazioneComposite> importGiocatoreRosaJpaItemWriter;
    private final ItemWriter<GiocatoreListoneGiocatoreComposite> importListoneJpaItemWriter;
    private final ApiGatewayFacade apiGatewayFacade;

    @Override
    public void write(Chunk<?> chunk) throws Exception {
        if (!chunk.isEmpty()) {
            Object firstItem = chunk.getItems().get(0);
            if (firstItem instanceof GiocatoreRosaOperazioneComposite) {
                importGiocatoreRosaJpaItemWriter.write((Chunk<? extends GiocatoreRosaOperazioneComposite>) chunk);
            } else if (firstItem instanceof GiocatoreListoneGiocatoreComposite) {
                importListoneJpaItemWriter.write((Chunk<? extends GiocatoreListoneGiocatoreComposite>) chunk);
            }else if (firstItem instanceof TrattativaScambio) {
                /*chunk.getItems().stream()
                        .map(TrattativaScambio.class::cast)
                        .forEach(apiGatewayFacade::createTrattativaScambio);*/
                chunk.getItems().stream()
                        .map(TrattativaScambio.class::cast)
                        .forEach(System.out::println);
            } else {
                throw new IllegalArgumentException("Tipo di oggetto non supportato: " + firstItem.getClass().getName());
            }
        }
     }
}
