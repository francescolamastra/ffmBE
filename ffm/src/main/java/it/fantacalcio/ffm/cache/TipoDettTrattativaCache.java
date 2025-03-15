package it.fantacalcio.ffm.cache;

import it.fantacalcio.ffm.domain.dto.TipoDettTrattativaDto;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
@Component
public class TipoDettTrattativaCache {
    private final List<TipoDettTrattativaDto> tipoDettTrattativaList = new CopyOnWriteArrayList<>();

    public void setTipoDettTrattativaList(List<TipoDettTrattativaDto> tipoDettTrattativaList) {
        this.tipoDettTrattativaList.clear();
        this.tipoDettTrattativaList.addAll(tipoDettTrattativaList);
    }

    public void addTipoDettTrattativa(TipoDettTrattativaDto tipoDettTrattativaDto) {
        this.tipoDettTrattativaList.add(tipoDettTrattativaDto);
    }

    public boolean isEmpty(){
        return this.tipoDettTrattativaList.isEmpty();
    }
}
