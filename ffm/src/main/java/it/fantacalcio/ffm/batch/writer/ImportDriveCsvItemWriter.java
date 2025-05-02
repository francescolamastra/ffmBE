package it.fantacalcio.ffm.batch.writer;

import it.fantacalcio.ffm.batch.model.GiocatoreListoneGiocatoreComposite;
import it.fantacalcio.ffm.batch.model.GiocatoreRosaOperazioneComposite;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

@RequiredArgsConstructor
public class ImportDriveCsvItemWriter implements ItemWriter<Object> {

    private final ItemWriter<GiocatoreRosaOperazioneComposite> importGiocatoreRosaJpaItemWriter;
    private final ItemWriter<GiocatoreListoneGiocatoreComposite> importListoneJpaItemWriter;

    @Override
    public void write(Chunk<?> chunk) throws Exception {
        if (!chunk.isEmpty()) {
            Object firstItem = chunk.getItems().get(0);
            if (firstItem instanceof GiocatoreRosaOperazioneComposite) {
                importGiocatoreRosaJpaItemWriter.write((Chunk<? extends GiocatoreRosaOperazioneComposite>) chunk);
                //chunk.forEach(System.out::println);
            } else if (firstItem instanceof GiocatoreListoneGiocatoreComposite) {
                importListoneJpaItemWriter.write((Chunk<? extends GiocatoreListoneGiocatoreComposite>) chunk);
                //chunk.forEach(System.out::println);
            } else {
                throw new IllegalArgumentException("Tipo di oggetto non supportato: " + firstItem.getClass().getName());
            }
        }
     }
}
