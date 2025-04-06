package it.fantacalcio.ffm.batch.writer;

import it.fantacalcio.ffm.domain.entity.Operazione;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ImportOperazioneJpaItemWriter implements ItemWriter<List<Operazione>> {

    private final JpaItemWriter<Operazione> operazioneJpaItemWriter;

    public ImportOperazioneJpaItemWriter(JpaItemWriter<Operazione> operazioneJpaItemWriter){
        this.operazioneJpaItemWriter = operazioneJpaItemWriter;
    }

    @Override
    public void write(Chunk<? extends List<Operazione>> chunk) throws Exception {
        chunk.forEach(listOperazione -> operazioneJpaItemWriter.write(new Chunk<>(listOperazione)));
     }
}
