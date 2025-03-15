package it.fantacalcio.ffm.cache;

import it.fantacalcio.ffm.domain.dto.StadioDto;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
@Component
public class StadioCache {
    private final List<StadioDto> stadioList = new CopyOnWriteArrayList<>();

    public void setStadioList(List<StadioDto> stadioList) {
        this.stadioList.clear();
        this.stadioList.addAll(stadioList);
    }

    public void addStadio(StadioDto stadioDto) {
        this.stadioList.add(stadioDto);
    }

    public boolean isEmpty(){
        return this.stadioList.isEmpty();
    }
}
