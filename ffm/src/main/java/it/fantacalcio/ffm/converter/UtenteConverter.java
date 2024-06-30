package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.domain.dto.UtenteDto;
import it.fantacalcio.ffm.domain.entity.Utente;
import org.springframework.stereotype.Component;

@Component
public class UtenteConverter {
    private UtenteConverter() {}

    public static UtenteDto toDto(Utente utente){
        return new UtenteDto(utente.getId(), utente.getIdFantagazzetta(), utente.getNome(),utente.getRuolo(), utente.getQuotazione());
    }

//    public static Utente toEntity(UtenteDto utente){
//        return new Utente(utente.getId(), utente.getIdFantagazzetta(), utente.getNome(),utente.getRuolo(), utente.getQuotazione());
//    }
}
