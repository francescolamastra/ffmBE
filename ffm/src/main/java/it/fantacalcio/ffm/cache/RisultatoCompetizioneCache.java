package it.fantacalcio.ffm.cache;

import it.fantacalcio.ffm.domain.dto.StagioneCompetizioneDto;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Component
public class RisultatoCompetizioneCache {
    private final Map<String, Boolean> risultatoCompetizioneMap = new ConcurrentHashMap<>();

    public void addRisultatoCompetizione(StagioneCompetizioneDto stagioneCompetizioneDto, Integer giornataSerieA, boolean exists) {
        String key = generateKey(stagioneCompetizioneDto.getId(), giornataSerieA);
        this.risultatoCompetizioneMap.put(key, exists);
    }

    public Boolean existsByStagioneCompetizioneAndGiornataSerieA(StagioneCompetizioneDto stagioneCompetizioneDto, Integer giornataSerieA) {
        String key = generateKey(stagioneCompetizioneDto.getId(), giornataSerieA);
        return this.risultatoCompetizioneMap.get(key);
    }

    private String generateKey(Integer idStagioneCompetizione, Integer giornataSerieA) {
        return idStagioneCompetizione + "-" + giornataSerieA;
    }
}
