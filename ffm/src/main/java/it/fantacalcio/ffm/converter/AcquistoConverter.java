package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.domain.dto.AcquistoDto;
import it.fantacalcio.ffm.domain.entity.Acquisto;

import java.util.IdentityHashMap;
import java.util.Map;

public class AcquistoConverter {
    private AcquistoConverter() {}

    public static AcquistoDto toDto(Acquisto acquisto) {
        return toDto(acquisto, new IdentityHashMap<>());
    }

    protected static AcquistoDto toDto(Acquisto acquisto, Map<Object, Object> context) {
        if (acquisto == null) {
            return null;
        }
        if (context.containsKey(acquisto)) {
            return (AcquistoDto) context.get(acquisto);
        }
        AcquistoDto acquistoDto = new AcquistoDto();
        context.put(acquisto, acquistoDto);
        acquistoDto.setId(acquisto.getId());
        acquistoDto.setIdOperazione(OperazioneConverter.toDto(acquisto.getIdOperazione(), context));
        acquistoDto.setAnniContratto(acquisto.getAnniContratto());
        return acquistoDto;
    }

    public static Acquisto toEntity(AcquistoDto acquistoDto) {
        return toEntity(acquistoDto, new IdentityHashMap<>());
    }

    protected static Acquisto toEntity(AcquistoDto acquistoDto, Map<Object, Object> context) {
        if (acquistoDto == null) {
            return null;
        }
        if (context.containsKey(acquistoDto)) {
            return (Acquisto) context.get(acquistoDto);
        }
        Acquisto acquistoEntity = new Acquisto();
        context.put(acquistoDto, acquistoEntity);
        acquistoEntity.setId(acquistoDto.getId());
        acquistoEntity.setIdOperazione(OperazioneConverter.toEntity(acquistoDto.getIdOperazione(), context));
        acquistoEntity.setAnniContratto(acquistoDto.getAnniContratto());
        return acquistoEntity;
    }
}
