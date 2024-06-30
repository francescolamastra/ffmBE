package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.domain.dto.FaseCompetizioneDto;
import it.fantacalcio.ffm.domain.entity.FaseCompetizione;
import org.springframework.stereotype.Component;

@Component
public class FaseCompetizioneConverter {
    private FaseCompetizioneConverter() {}

    public static FaseCompetizioneDto toDto(FaseCompetizione faseCompetizione){
        return new FaseCompetizioneDto(faseCompetizione.getId(), faseCompetizione.getIdFantagazzetta(), faseCompetizione.getNome(),faseCompetizione.getRuolo(), faseCompetizione.getQuotazione());
    }

//    public static FaseCompetizione toEntity(FaseCompetizioneDto faseCompetizione){
//        return new FaseCompetizione(faseCompetizione.getId(), faseCompetizione.getIdFantagazzetta(), faseCompetizione.getNome(),faseCompetizione.getRuolo(), faseCompetizione.getQuotazione());
//    }
}
