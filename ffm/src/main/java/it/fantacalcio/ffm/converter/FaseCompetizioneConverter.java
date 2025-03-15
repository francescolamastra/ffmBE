package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.domain.dto.FaseCompetizioneDto;
import it.fantacalcio.ffm.domain.entity.FaseCompetizione;

public class FaseCompetizioneConverter {
    private FaseCompetizioneConverter() {}

    public static FaseCompetizioneDto toDto(FaseCompetizione faseCompetizione){
        return new FaseCompetizioneDto(faseCompetizione.getId(),
                faseCompetizione.getSigla(),
                faseCompetizione.getDescrizione());
    }

    public static FaseCompetizione toEntity(FaseCompetizioneDto faseCompetizione){
        FaseCompetizione faseCompetizioneEntity = new FaseCompetizione();
        faseCompetizioneEntity.setId(faseCompetizione.getId());
        faseCompetizioneEntity.setSigla(faseCompetizione.getSigla());
        faseCompetizioneEntity.setDescrizione(faseCompetizione.getDescrizione());
        return faseCompetizioneEntity;
    }
}
