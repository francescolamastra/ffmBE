package it.fantacalcio.ffm.cache;

import it.fantacalcio.ffm.domain.dto.TipoOperazioneDto;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
@Component
public class TipoOperazioneCache {
    private final List<TipoOperazioneDto> tipiOperazione = new CopyOnWriteArrayList<>();

    public void setTipiOperazione(List<TipoOperazioneDto> tipiOperazione) {
        this.tipiOperazione.clear();
        this.tipiOperazione.addAll(tipiOperazione);
    }

    public void addTipoOperazione(TipoOperazioneDto tipoOperazioneDto) {
        this.tipiOperazione.add(tipoOperazioneDto);
    }

    public boolean isEmpty(){
        return this.tipiOperazione.isEmpty();
    }
}
