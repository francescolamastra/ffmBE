package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.domain.dto.StagioneCompetizioneDto;
import it.fantacalcio.ffm.domain.entity.StagioneCompetizione;
import org.springframework.stereotype.Component;

@Component
public class StagioneCompetizioneConverter {
    private StagioneCompetizioneConverter() {}

    public static StagioneCompetizioneDto toDto(StagioneCompetizione stagioneCompetizione){
        return new StagioneCompetizioneDto(stagioneCompetizione.getId(),
                StagioneConverter.toDto(stagioneCompetizione.getIdStagione()),
                CompetizioneConverter.toDto(stagioneCompetizione.getIdCompetizione()));
    }

    public static StagioneCompetizione toEntity(StagioneCompetizioneDto stagioneCompetizione){
        StagioneCompetizione stagioneCompetizioneEntity = new StagioneCompetizione();
        stagioneCompetizioneEntity.setId(stagioneCompetizione.getId());
        stagioneCompetizioneEntity.setIdStagione(StagioneConverter.toEntity(stagioneCompetizione.getIdStagione()));
        stagioneCompetizioneEntity.setIdCompetizione(CompetizioneConverter.toEntity(stagioneCompetizione.getIdCompetizione()));
        return stagioneCompetizioneEntity;
    }
}
