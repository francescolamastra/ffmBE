package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.domain.dto.StagioneCompetizioneDto;
import it.fantacalcio.ffm.domain.entity.StagioneCompetizione;
import org.springframework.stereotype.Component;

@Component
public class StagioneCompetizioneConverter {
    private StagioneCompetizioneConverter() {}

    public static StagioneCompetizioneDto toDto(StagioneCompetizione stagioneCompetizione){
        return new StagioneCompetizioneDto(stagioneCompetizione.getId(), stagioneCompetizione.getIdFantagazzetta(), stagioneCompetizione.getNome(),stagioneCompetizione.getRuolo(), stagioneCompetizione.getQuotazione());
    }

//    public static StagioneCompetizione toEntity(StagioneCompetizioneDto stagioneCompetizione){
//        return new StagioneCompetizione(stagioneCompetizione.getId(), stagioneCompetizione.getIdFantagazzetta(), stagioneCompetizione.getNome(),stagioneCompetizione.getRuolo(), stagioneCompetizione.getQuotazione());
//    }
}
