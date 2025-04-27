package it.fantacalcio.ffm.batch.writer;

import it.fantacalcio.ffm.batch.model.GiocatoreRosaOperazioneComposite;
import it.fantacalcio.ffm.converter.OperazioneConverter;
import it.fantacalcio.ffm.domain.entity.GiocatoreRosa;
import it.fantacalcio.ffm.domain.entity.Operazione;
import it.fantacalcio.ffm.mapper.GiocatoreRosaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;

@RequiredArgsConstructor
public class ImportGiocatoreRosaJpaItemWriter implements ItemWriter<GiocatoreRosaOperazioneComposite> {

    private final JpaItemWriter<GiocatoreRosa> giocatoreRosaJpaItemWriter;
    private final JpaItemWriter<Operazione> operazioneJpaItemWriter;

    @Override
    public void write(Chunk<? extends GiocatoreRosaOperazioneComposite> chunk) {
        // Scrive i GiocatoreRosa
        giocatoreRosaJpaItemWriter.write(new Chunk<>(
                chunk.getItems().stream()
                        .flatMap(composite -> composite.getGiocatoreRosaDtoList().stream())
                        .map(GiocatoreRosaMapper.INSTANCE::toEntity)
                        .toList()
        ));
        // Scrive le Operazioni
        operazioneJpaItemWriter.write(new Chunk<>(
                chunk.getItems().stream()
                        .flatMap(composite -> composite.getOperazioneDtoList().stream())
                        .map(OperazioneConverter::toEntity)
                        .toList()
        ));
     }
}
