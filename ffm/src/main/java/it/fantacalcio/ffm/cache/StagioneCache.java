package it.fantacalcio.ffm.cache;

import it.fantacalcio.ffm.domain.dto.StagioneDto;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
@Component
public class StagioneCache {
    private final List<StagioneDto> stagioni = new CopyOnWriteArrayList<>();

    public void setStagioni(List<StagioneDto> stagioni) {
        this.stagioni.clear();
        this.stagioni.addAll(stagioni);
    }

    public void addStagione(StagioneDto stagioneDto) {
        this.stagioni.add(stagioneDto);
    }

    public boolean isEmpty(){
        return this.stagioni.isEmpty();
    }
}
