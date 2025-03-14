package it.fantacalcio.ffm.batch.writer;

import it.fantacalcio.ffm.batch.model.ListoneGiocatoreDtoWrapper;
import it.fantacalcio.ffm.converter.GiocatoreConverter;
import it.fantacalcio.ffm.converter.ListoneConverter;
import it.fantacalcio.ffm.domain.entity.Giocatore;
import it.fantacalcio.ffm.domain.entity.Listone;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;

public class ImportListoneJpaItemWriter implements ItemWriter<ListoneGiocatoreDtoWrapper> {

    private final JpaItemWriter<Giocatore> giocatoreItemWriter;
    private final JpaItemWriter<Listone> listoneItemWriter;

    public ImportListoneJpaItemWriter(JpaItemWriter<Giocatore> giocatoreItemWriter, JpaItemWriter<Listone> listoneItemWriter){
        this.giocatoreItemWriter = giocatoreItemWriter;
        this.listoneItemWriter = listoneItemWriter;
    }

    @Override
    public void write(Chunk<? extends ListoneGiocatoreDtoWrapper> chunk) throws Exception {
        for (ListoneGiocatoreDtoWrapper wrapper : chunk) {
            giocatoreItemWriter.write(Chunk.of(GiocatoreConverter.toEntity(wrapper.getGiocatoreDto())));
            listoneItemWriter.write(Chunk.of(ListoneConverter.toEntity(wrapper.getListoneDto())));
        }
    }
}
