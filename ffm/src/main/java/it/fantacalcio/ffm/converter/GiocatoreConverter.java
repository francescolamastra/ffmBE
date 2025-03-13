package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.domain.dto.GiocatoreDto;
import it.fantacalcio.ffm.domain.entity.Giocatore;
import org.springframework.stereotype.Component;

@Component
public class GiocatoreConverter {
    private GiocatoreConverter() {}

    public static GiocatoreDto toDto(Giocatore giocatore){
        return new GiocatoreDto(giocatore.getId(),
                giocatore.getIdFantagazzetta(),
                giocatore.getNome(),
                giocatore.getRuolo());
    }

    public static Giocatore toEntity(GiocatoreDto giocatore){
        Giocatore giocatoreEntity = new Giocatore();
        giocatoreEntity.setId(giocatore.id());
        giocatoreEntity.setNome(giocatore.nome());
        giocatoreEntity.setRuolo(giocatore.ruolo());
        giocatoreEntity.setIdFantagazzetta(giocatore.idFantagazzetta());
        return giocatoreEntity;
    }
}
