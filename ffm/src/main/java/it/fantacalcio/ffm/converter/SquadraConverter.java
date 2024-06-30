package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.domain.dto.SquadraDto;
import it.fantacalcio.ffm.domain.entity.Squadra;
import org.springframework.stereotype.Component;

@Component
public class SquadraConverter {
    private SquadraConverter() {}

    public static SquadraDto toDto(Squadra squadra){
        return new SquadraDto(squadra.getId(), squadra.getIdFantagazzetta(), squadra.getNome(),squadra.getRuolo(), squadra.getQuotazione());
    }

//    public static Squadra toEntity(SquadraDto squadra){
//        return new Squadra(squadra.getId(), squadra.getIdFantagazzetta(), squadra.getNome(),squadra.getRuolo(), squadra.getQuotazione());
//    }
}
