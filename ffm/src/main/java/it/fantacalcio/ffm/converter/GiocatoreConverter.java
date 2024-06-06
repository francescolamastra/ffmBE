package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.domain.dto.GiocatoreDto;
import it.fantacalcio.ffm.domain.entity.Giocatore;
import org.springframework.stereotype.Component;

@Component
public class GiocatoreConverter {
    private GiocatoreConverter() {}

    public static GiocatoreDto toDto(Giocatore giocatore){
        return new GiocatoreDto(giocatore.getId(), giocatore.getIdFantagazzetta(), giocatore.getNome(),giocatore.getRuolo(), giocatore.getQuotazione());
    }

//    public static Giocatore toEntity(GiocatoreDto giocatore){
//        return new Giocatore(giocatore.getId(), giocatore.getIdFantagazzetta(), giocatore.getNome(),giocatore.getRuolo(), giocatore.getQuotazione());
//    }
}
