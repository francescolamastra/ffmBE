package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.domain.dto.SvincoloDto;
import it.fantacalcio.ffm.domain.entity.Svincolo;

import java.util.IdentityHashMap;
import java.util.Map;

public class SvincoloConverter {
    private SvincoloConverter() {}

    public static SvincoloDto toDto(Svincolo svincolo){
        return toDto(svincolo, new IdentityHashMap<>());
    }

    protected static SvincoloDto toDto(Svincolo svincolo, Map<Object, Object> context){
        if (svincolo == null) {
            return null;
        }
        if (context.containsKey(svincolo)) {
            return (SvincoloDto) context.get(svincolo);
        }
        SvincoloDto svincoloDto = new SvincoloDto();
        context.put(svincolo, svincoloDto);
        svincoloDto.setId(svincolo.getId());
        svincoloDto.setIdOperazione(OperazioneConverter.toDto(svincolo.getIdOperazione(), context));
        svincoloDto.setPercentuale(svincolo.getPercentuale());
        return svincoloDto;
    }

    public static Svincolo toEntity(SvincoloDto svincoloDto){
        return toEntity(svincoloDto, new IdentityHashMap<>());
    }

    protected static Svincolo toEntity(SvincoloDto svincoloDto, Map<Object, Object> context){
        if (svincoloDto == null) {
            return null;
        }
        if (context.containsKey(svincoloDto)) {
            return (Svincolo) context.get(svincoloDto);
        }
        Svincolo svincoloEntity = new Svincolo();
        context.put(svincoloDto, svincoloEntity);
        svincoloEntity.setId(svincoloDto.getId());
        svincoloEntity.setIdOperazione(OperazioneConverter.toEntity(svincoloDto.getIdOperazione(), context));
        svincoloEntity.setPercentuale(svincoloDto.getPercentuale());
        return svincoloEntity;
    }
}
