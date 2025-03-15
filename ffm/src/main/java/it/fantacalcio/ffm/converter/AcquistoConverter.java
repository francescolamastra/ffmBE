package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.domain.dto.AcquistoDto;
import it.fantacalcio.ffm.domain.entity.Acquisto;

public class AcquistoConverter {
    private AcquistoConverter() {}

    public static AcquistoDto toDto(Acquisto acquisto){
        return new AcquistoDto(acquisto.getId(),
                OperazioneConverter.toDto(acquisto.getIdOperazione()),
                acquisto.getAnniContratto());
    }

    public static Acquisto toEntity(AcquistoDto acquistoDto){
        Acquisto acquistoEntity = new Acquisto();
        acquistoEntity.setId(acquistoDto.getId());
        acquistoEntity.setIdOperazione(OperazioneConverter.toEntity(acquistoDto.getIdOperazione()));
        acquistoEntity.setAnniContratto(acquistoDto.getAnniContratto());
        return acquistoEntity;
    }
}
