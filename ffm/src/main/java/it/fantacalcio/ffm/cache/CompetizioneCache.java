package it.fantacalcio.ffm.cache;

import it.fantacalcio.ffm.domain.dto.CompetizioneDto;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
@Component
public class CompetizioneCache {
    private final List<CompetizioneDto> listCompetizioni = new CopyOnWriteArrayList<>();

    public void setListCompetizioni(List<CompetizioneDto> listCompetizioni) {
        this.listCompetizioni.clear();
        this.listCompetizioni.addAll(listCompetizioni);
    }

    public void addCompetizione(CompetizioneDto competizioneDto) {
        this.listCompetizioni.add(competizioneDto);
    }

    public boolean isEmpty(){
        return this.listCompetizioni.isEmpty();
    }
}
