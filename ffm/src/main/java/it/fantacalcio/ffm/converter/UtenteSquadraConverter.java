package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.domain.dto.UtenteSquadraDto;
import it.fantacalcio.ffm.domain.entity.UtenteSquadra;
import org.springframework.stereotype.Component;

@Component
public class UtenteSquadraConverter {
    private UtenteSquadraConverter() {}

    public static UtenteSquadraDto toDto(UtenteSquadra utenteSquadra){
        return new UtenteSquadraDto(utenteSquadra.getId(), utenteSquadra.getIdFantagazzetta(), utenteSquadra.getNome(),utenteSquadra.getRuolo(), utenteSquadra.getQuotazione());
    }

//    public static UtenteSquadra toEntity(UtenteSquadraDto utenteSquadra){
//        return new UtenteSquadra(utenteSquadra.getId(), utenteSquadra.getIdFantagazzetta(), utenteSquadra.getNome(),utenteSquadra.getRuolo(), utenteSquadra.getQuotazione());
//    }
}
