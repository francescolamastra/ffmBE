package it.fantacalcio.ffm.batch.writer;

import it.fantacalcio.ffm.domain.entity.Operazione;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;

import java.util.List;

@RequiredArgsConstructor
public class ImportOperazioneJpaItemWriter implements ItemWriter<List<Operazione>> {

    private final JpaItemWriter<Operazione> operazioneJpaItemWriter;

    @Override
    public void write(Chunk<? extends List<Operazione>> chunk) throws Exception {
        chunk.forEach(listOperazione -> operazioneJpaItemWriter.write(new Chunk<>(listOperazione)));
     }
}
