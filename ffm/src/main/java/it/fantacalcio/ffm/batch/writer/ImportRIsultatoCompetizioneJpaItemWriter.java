package it.fantacalcio.ffm.batch.writer;

import it.fantacalcio.ffm.batch.model.MatchRecord;
import it.fantacalcio.ffm.domain.entity.RisultatoCompetizione;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Stream;

@Component
public class ImportRIsultatoCompetizioneJpaItemWriter implements ItemWriter<MatchRecord> {

    private final JpaItemWriter<RisultatoCompetizione> risultatoCompetizioneJpaItemWriter;

    public ImportRIsultatoCompetizioneJpaItemWriter(JpaItemWriter<RisultatoCompetizione> risultatoCompetizioneJpaItemWriter){
        this.risultatoCompetizioneJpaItemWriter = risultatoCompetizioneJpaItemWriter;
    }

    @Override
    public void write(Chunk<? extends MatchRecord> chunk) throws Exception {
        List<RisultatoCompetizione> risultati = chunk.getItems().stream()
                .flatMap(matchRecord -> Stream.of(matchRecord.getRisultatoCompetizioneSquadraA(), matchRecord.getRisultatoCompetizioneSquadraB()))
                .toList();
        risultatoCompetizioneJpaItemWriter.write(new Chunk<>(risultati));
    }
}
