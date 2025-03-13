package it.fantacalcio.ffm.batch.processor;

import it.fantacalcio.ffm.converter.GiocatoreConverter;
import it.fantacalcio.ffm.domain.dto.GiocatoreDto;
import it.fantacalcio.ffm.domain.entity.Giocatore;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class ExcelItemProcessor implements ItemProcessor<GiocatoreDto, Giocatore> {

    @Override
    public Giocatore process(GiocatoreDto item) {
        // Trasforma i dati se necessario
        return GiocatoreConverter.toEntity(item);
    }
}
