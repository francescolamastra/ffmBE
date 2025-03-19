package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.domain.dto.PrestitoDto;
import it.fantacalcio.ffm.domain.entity.Prestito;

import java.util.IdentityHashMap;
import java.util.Map;

public class PrestitoConverter {
    private PrestitoConverter() {}

    public static PrestitoDto toDto(Prestito prestito){
        return toDto(prestito, new IdentityHashMap<>());
    }

    public static PrestitoDto toDto(Prestito prestito, Map<Object, Object> context){
        if (prestito == null) {
            return null;
        }
        if (context.containsKey(prestito)) {
            return (PrestitoDto) context.get(prestito);
        }
        PrestitoDto prestitoDto = new PrestitoDto();
        context.put(prestito, prestitoDto);
        prestitoDto.setId(prestito.getId());
        prestitoDto.setIdDettTrattativa(DettaglioTrattativaConverter.toDto(prestito.getIdDettTrattativa(), context));
        prestitoDto.setCostoRiscatto(prestito.getCostoRiscatto());
        prestitoDto.setObbligo(prestito.getObbligo()!= null ? prestito.getObbligo() : false);
        prestitoDto.setRiscatto(prestito.getRiscatto()!= null ? prestito.getRiscatto() : false);
        prestitoDto.setEsercitato(prestito.getEsercitato()!= null ? prestito.getEsercitato() : false);
        return prestitoDto;
    }

    public static Prestito toEntity(PrestitoDto prestitoDto){
        return toEntity(prestitoDto, new IdentityHashMap<>());
    }

    public static Prestito toEntity(PrestitoDto prestitoDto, Map<Object, Object> context){
        if (prestitoDto == null) {
            return null;
        }
        if (context.containsKey(prestitoDto)) {
            return (Prestito) context.get(prestitoDto);
        }
        Prestito prestitoEntity = new Prestito();
        context.put(prestitoDto, prestitoEntity);
        prestitoEntity.setId(prestitoDto.getId());
        prestitoEntity.setIdDettTrattativa(DettaglioTrattativaConverter.toEntity(prestitoDto.getIdDettTrattativa(), context));
        prestitoEntity.setObbligo(prestitoDto.getObbligo() != null ? prestitoDto.getObbligo() : false);
        prestitoEntity.setEsercitato(prestitoDto.getEsercitato() != null ? prestitoDto.getEsercitato() : false);
        prestitoEntity.setRiscatto(prestitoDto.getRiscatto() != null ? prestitoDto.getRiscatto() : false);
        prestitoEntity.setCostoRiscatto(prestitoDto.getCostoRiscatto());
        return prestitoEntity;
    }
}
