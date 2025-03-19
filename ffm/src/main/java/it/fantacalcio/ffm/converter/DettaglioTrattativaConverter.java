package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.domain.dto.DettaglioTrattativaDto;
import it.fantacalcio.ffm.domain.entity.DettaglioTrattativa;

import java.util.IdentityHashMap;
import java.util.Map;

public class DettaglioTrattativaConverter {
    private DettaglioTrattativaConverter() {}

    public static DettaglioTrattativaDto toDto(DettaglioTrattativa dettaglioTrattativa) {
        return toDto(dettaglioTrattativa, new IdentityHashMap<>());
    }

    protected static DettaglioTrattativaDto toDto(DettaglioTrattativa dettaglioTrattativa, Map<Object, Object> context){
        if (dettaglioTrattativa == null) {
            return null;
        }
        if (context.containsKey(dettaglioTrattativa)) {
            return (DettaglioTrattativaDto) context.get(dettaglioTrattativa);
        }
        DettaglioTrattativaDto dettaglioTrattativaDto = new DettaglioTrattativaDto();
        context.put(dettaglioTrattativa, dettaglioTrattativaDto);
        dettaglioTrattativaDto.setId(dettaglioTrattativa.getId());
        dettaglioTrattativaDto.setIdTrattativa(TrattativaConverter.toDto(dettaglioTrattativa.getIdTrattativa()));
        dettaglioTrattativaDto.setIdTipoDettTrattativa(TipoDettTrattativaConverter.toDto(dettaglioTrattativa.getIdTipoDettTrattativa()));
        dettaglioTrattativaDto.setIdSquadra(SquadraConverter.toDto(dettaglioTrattativa.getIdSquadra()));
        dettaglioTrattativaDto.setIdGiocatore(GiocatoreConverter.toDto(dettaglioTrattativa.getIdGiocatore()));
        dettaglioTrattativaDto.setIdTipoOperazione(TipoOperazioneConverter.toDto(dettaglioTrattativa.getIdTipoOperazione()));
        if(dettaglioTrattativa.getPrestito() != null){
            dettaglioTrattativaDto.setIdPrestito(PrestitoConverter.toDto(dettaglioTrattativa.getPrestito(), context));
        }
        return dettaglioTrattativaDto;
    }

    public static DettaglioTrattativa toEntity(DettaglioTrattativaDto dettaglioTrattativaDto) {
        return toEntity(dettaglioTrattativaDto, new IdentityHashMap<>());
    }

    protected static DettaglioTrattativa toEntity(DettaglioTrattativaDto dettaglioTrattativaDto, Map<Object, Object> context){
        if (dettaglioTrattativaDto == null) {
            return null;
        }
        if (context.containsKey(dettaglioTrattativaDto)) {
            return (DettaglioTrattativa) context.get(dettaglioTrattativaDto);
        }
        DettaglioTrattativa dettaglioTrattativaEntity = new DettaglioTrattativa();
        context.put(dettaglioTrattativaDto, dettaglioTrattativaEntity);
        dettaglioTrattativaEntity.setId(dettaglioTrattativaDto.getId());
        dettaglioTrattativaEntity.setIdTrattativa(TrattativaConverter.toEntity(dettaglioTrattativaDto.getIdTrattativa()));
        dettaglioTrattativaEntity.setIdTipoDettTrattativa(TipoDettTrattativaConverter.toEntity(dettaglioTrattativaDto.getIdTipoDettTrattativa()));
        dettaglioTrattativaEntity.setIdSquadra(SquadraConverter.toEntity(dettaglioTrattativaDto.getIdSquadra()));
        dettaglioTrattativaEntity.setIdGiocatore(GiocatoreConverter.toEntity(dettaglioTrattativaDto.getIdGiocatore()));
        dettaglioTrattativaEntity.setIdTipoOperazione(TipoOperazioneConverter.toEntity(dettaglioTrattativaDto.getIdTipoOperazione()));
        if(dettaglioTrattativaDto.getIdPrestito() != null){
            dettaglioTrattativaEntity.setPrestito(PrestitoConverter.toEntity(dettaglioTrattativaDto.getIdPrestito(), context));
        }
        return dettaglioTrattativaEntity;
    }
}
