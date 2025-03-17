package it.fantacalcio.ffm.cache;

import it.fantacalcio.ffm.domain.dto.SquadraDto;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
@Component
public class SquadraCache {
    private final List<SquadraDto> squadraList = new CopyOnWriteArrayList<>();

    public void addSquadra(SquadraDto squadraDto) {
        this.squadraList.clear();
        this.squadraList.add(squadraDto);
    }

    public boolean isEmpty(){
        return this.squadraList.isEmpty();
    }
}
