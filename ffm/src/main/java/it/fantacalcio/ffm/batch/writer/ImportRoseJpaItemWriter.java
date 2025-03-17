package it.fantacalcio.ffm.batch.writer;

import it.fantacalcio.ffm.domain.dto.OperazioneDto;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

public class ImportRoseJpaItemWriter implements ItemWriter<OperazioneDto> {

    @Override
    public void write(Chunk<? extends OperazioneDto> chunk) throws Exception {
        for (OperazioneDto record : chunk) {
            System.out.println(record);
        }
    }
}
