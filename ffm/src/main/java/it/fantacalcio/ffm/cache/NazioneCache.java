package it.fantacalcio.ffm.cache;

import it.fantacalcio.ffm.domain.dto.NazioneDto;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
@Component
public class NazioneCache {
    private final List<NazioneDto> nazioni = new CopyOnWriteArrayList<>();

    public void setNazioni(List<NazioneDto> nazioni) {
        this.nazioni.clear();
        this.nazioni.addAll(nazioni);
    }

    public void addNazione(NazioneDto nazioneDto) {
        this.nazioni.add(nazioneDto);
    }

    public boolean isEmpty(){
        return this.nazioni.isEmpty();
    }
}
