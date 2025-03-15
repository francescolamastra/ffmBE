package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.domain.dto.SvincoloDto;
import it.fantacalcio.ffm.domain.entity.Svincolo;

public class SvincoloConverter {
    private SvincoloConverter() {}

    public static SvincoloDto toDto(Svincolo svincolo){
        return new SvincoloDto(svincolo.getId(),
                OperazioneConverter.toDto(svincolo.getIdOperazione()),
                svincolo.getPercentuale());
    }

    public static Svincolo toEntity(SvincoloDto svincolo){
        Svincolo svincoloEntity = new Svincolo();
        svincoloEntity.setId(svincolo.getId());
        svincoloEntity.setIdOperazione(OperazioneConverter.toEntity(svincolo.getIdOperazione()));
        svincoloEntity.setPercentuale(svincolo.getPercentuale());
        return svincoloEntity;
    }
}
