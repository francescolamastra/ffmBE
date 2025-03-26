package it.fantacalcio.ffm.cache;

import it.fantacalcio.ffm.domain.dto.FaseCompetizioneDto;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
@Component
public class FaseCompetizioneCache {
    private final List<FaseCompetizioneDto> faseCompetizioneList = new CopyOnWriteArrayList<>();

    public void setFaseCompetizioneList(List<FaseCompetizioneDto> faseCompetizioneList) {
        this.faseCompetizioneList.clear();
        this.faseCompetizioneList.addAll(faseCompetizioneList);
    }

    public void addFaseCompetizione(FaseCompetizioneDto faseCompetizioneDto) {
        this.faseCompetizioneList.add(faseCompetizioneDto);
    }

    public boolean isEmpty(){
        return this.faseCompetizioneList.isEmpty();
    }
}
