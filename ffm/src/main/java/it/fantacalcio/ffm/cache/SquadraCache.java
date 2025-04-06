package it.fantacalcio.ffm.cache;

import it.fantacalcio.ffm.domain.dto.SquadraDto;
import it.fantacalcio.ffm.utility.Constants;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
@Component
public class SquadraCache {
    private final List<SquadraDto> squadraList = new CopyOnWriteArrayList<>();

    public SquadraDto addSquadra(SquadraDto squadraDto) {
        if(this.squadraList.size() == Constants.TOT_SQUADRE_CAT_A){
            this.squadraList.clear();
        }
        this.squadraList.add(squadraDto);
        return squadraDto;
    }

    public boolean isEmpty(){
        return this.squadraList.isEmpty();
    }
}
