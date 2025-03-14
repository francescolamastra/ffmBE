package it.fantacalcio.ffm.batch.writer;

import it.fantacalcio.ffm.batch.model.RosaBatchRecord;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class ImportRoseJpaItemWriter implements ItemWriter<RosaBatchRecord> {

    @Override
    public void write(Chunk<? extends RosaBatchRecord> chunk) throws Exception {
        for (RosaBatchRecord record : chunk) {
            System.out.println(record);
        }
    }
}
