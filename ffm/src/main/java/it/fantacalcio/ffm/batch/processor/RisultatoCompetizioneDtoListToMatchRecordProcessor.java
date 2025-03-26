package it.fantacalcio.ffm.batch.processor;

import it.fantacalcio.ffm.batch.model.MatchRecord;
import it.fantacalcio.ffm.converter.RisultatoCompetizioneConverter;
import it.fantacalcio.ffm.domain.dto.RisultatoCompetizioneDto;
import it.fantacalcio.ffm.domain.entity.RisultatoCompetizione;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RisultatoCompetizioneDtoListToMatchRecordProcessor implements ItemProcessor<List<RisultatoCompetizioneDto>, MatchRecord> {

    @Override
    public MatchRecord process(List<RisultatoCompetizioneDto> items) throws Exception {
        if(items.size() > 2) throw new Exception("RisultatoCompetizioneDtoListToMatchRecordProcessor lista risultati singolo match maggiore di 2");
        return new MatchRecord(createRisultatoCompetizione(items.get(0)),createRisultatoCompetizione(items.get(1)));
    }

    private RisultatoCompetizione createRisultatoCompetizione(RisultatoCompetizioneDto risultatoCompetizioneDto){
        return RisultatoCompetizioneConverter.toEntity(risultatoCompetizioneDto);
    }
}
