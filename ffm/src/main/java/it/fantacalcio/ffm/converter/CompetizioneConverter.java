package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.domain.dto.CompetizioneDto;
import it.fantacalcio.ffm.domain.entity.Competizione;

public class CompetizioneConverter {
    private CompetizioneConverter() {}

    public static CompetizioneDto toDto(Competizione competizione){
        return new CompetizioneDto(competizione.getId(),
                competizione.getSigla(),
                competizione.getDescrizione());
    }

    public static Competizione toEntity(CompetizioneDto competizione){
        Competizione competizioneEntity = new Competizione();
        competizioneEntity.setId(competizione.getId());
        competizioneEntity.setSigla(competizione.getSigla());
        competizioneEntity.setDescrizione(competizione.getDescrizione());
        return competizioneEntity;
    }
}
