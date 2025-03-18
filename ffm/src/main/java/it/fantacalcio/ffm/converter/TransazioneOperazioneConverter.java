package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.domain.dto.TransazioneOperazioneDto;
import it.fantacalcio.ffm.domain.entity.TransazioneOperazione;

import java.util.IdentityHashMap;
import java.util.Map;

public class TransazioneOperazioneConverter {
    private TransazioneOperazioneConverter() {}

    public static TransazioneOperazioneDto toDto(TransazioneOperazione transazioneOperazione){
        return toDto(transazioneOperazione, new IdentityHashMap<>());
    }

    protected static TransazioneOperazioneDto toDto(TransazioneOperazione transazioneOperazione, Map<Object, Object> context){
        if (transazioneOperazione == null) {
            return null;
        }
        if (context.containsKey(transazioneOperazione)) {
            return (TransazioneOperazioneDto) context.get(transazioneOperazione);
        }
        TransazioneOperazioneDto transazioneOperazioneDto = new TransazioneOperazioneDto();
        context.put(transazioneOperazione, transazioneOperazioneDto);
        transazioneOperazioneDto.setId(transazioneOperazione.getId());
        transazioneOperazioneDto.setIdOperazione(OperazioneConverter.toDto(transazioneOperazione.getIdOperazione(), context));
        transazioneOperazioneDto.setImporto(transazioneOperazione.getImporto());
        transazioneOperazioneDto.setSegnoTransazione(transazioneOperazione.getSegnoTransazione());
        return transazioneOperazioneDto;
    }

    public static TransazioneOperazione toEntity(TransazioneOperazioneDto transazioneOperazioneDto){
        return toEntity(transazioneOperazioneDto, new IdentityHashMap<>());
    }

    protected static TransazioneOperazione toEntity(TransazioneOperazioneDto transazioneOperazioneDto, Map<Object, Object> context){
        if (transazioneOperazioneDto == null) {
            return null;
        }
        if (context.containsKey(transazioneOperazioneDto)) {
            return (TransazioneOperazione) context.get(transazioneOperazioneDto);
        }
        TransazioneOperazione transazioneOperazioneEntity = new TransazioneOperazione();
        context.put(transazioneOperazioneDto, transazioneOperazioneEntity);
        transazioneOperazioneEntity.setId(transazioneOperazioneDto.getId());
        transazioneOperazioneEntity.setIdOperazione(OperazioneConverter.toEntity(transazioneOperazioneDto.getIdOperazione(), context));
        transazioneOperazioneEntity.setSegnoTransazione(transazioneOperazioneDto.getSegnoTransazione());
        transazioneOperazioneEntity.setImporto(transazioneOperazioneDto.getImporto());
        return transazioneOperazioneEntity;
    }
}
