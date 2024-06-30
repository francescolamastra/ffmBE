package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.domain.dto.StadioDto;
import it.fantacalcio.ffm.domain.entity.Stadio;
import org.springframework.stereotype.Component;

@Component
public class StadioConverter {
    private StadioConverter() {}

    public static StadioDto toDto(Stadio stadio){
        return new StadioDto(stadio.getId(), stadio.getIdFantagazzetta(), stadio.getNome(),stadio.getRuolo(), stadio.getQuotazione());
    }

//    public static Stadio toEntity(StadioDto stadio){
//        return new Stadio(stadio.getId(), stadio.getIdFantagazzetta(), stadio.getNome(),stadio.getRuolo(), stadio.getQuotazione());
//    }
}
