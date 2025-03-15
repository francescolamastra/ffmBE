package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.domain.dto.PrestitoDto;
import it.fantacalcio.ffm.domain.entity.Prestito;

public class PrestitoConverter {
    private PrestitoConverter() {}

    public static PrestitoDto toDto(Prestito prestito){
        return new PrestitoDto(prestito.getId(),
                OperazioneConverter.toDto(prestito.getIdOperazione()),
                SquadraConverter.toDto(prestito.getIdSquadraCedente()),
                prestito.getCostoRiscatto(),
                prestito.getObbligo(),
                prestito.getRiscatto(),
                prestito.getEsercitato());
    }

    public static Prestito toEntity(PrestitoDto prestito){
        Prestito prestitoEntity = new Prestito();
        prestitoEntity.setId(prestito.getId());
        prestitoEntity.setIdOperazione(OperazioneConverter.toEntity(prestito.getIdOperazione()));
        prestitoEntity.setObbligo(prestito.getObbligo());
        prestitoEntity.setEsercitato(prestito.getEsercitato());
        prestitoEntity.setRiscatto(prestito.getRiscatto());
        prestitoEntity.setCostoRiscatto(prestito.getCostoRiscatto());
        prestitoEntity.setIdSquadraCedente(SquadraConverter.toEntity(prestito.getIdSquadraCedente()));
        return prestitoEntity;
    }
}
