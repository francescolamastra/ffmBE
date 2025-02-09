package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.domain.dto.UtenteSquadraDto;
import it.fantacalcio.ffm.domain.entity.UtenteSquadra;
import org.springframework.stereotype.Component;

@Component
public class UtenteSquadraConverter {
    private UtenteSquadraConverter() {}

    public static UtenteSquadraDto toDto(UtenteSquadra utenteSquadra){
        return new UtenteSquadraDto(utenteSquadra.getId(),
                UtenteConverter.toDto(utenteSquadra.getIdUtente()),
                SquadraConverter.toDto(utenteSquadra.getIdSquadra()),
                utenteSquadra.getDataCreazione());
    }

    public static UtenteSquadra toEntity(UtenteSquadraDto utenteSquadraDto) {
        UtenteSquadra utenteSquadraEntity = new UtenteSquadra();
        utenteSquadraEntity.setId(utenteSquadraDto.getId());
        utenteSquadraEntity.setIdUtente(UtenteConverter.toEntity(utenteSquadraDto.getIdUtente()));
        utenteSquadraEntity.setIdSquadra(SquadraConverter.toEntity(utenteSquadraDto.getIdSquadra()));
        return utenteSquadraEntity;
    }
}
