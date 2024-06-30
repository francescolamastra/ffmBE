package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.domain.dto.GettoneDto;
import it.fantacalcio.ffm.domain.entity.Gettone;
import org.springframework.stereotype.Component;

@Component
public class GettoneConverter {
    private GettoneConverter() {}

    public static GettoneDto toDto(Gettone gettone){
        return new GettoneDto(gettone.getId(), gettone.getIdFantagazzetta(), gettone.getNome(),gettone.getRuolo(), gettone.getQuotazione());
    }

//    public static Gettone toEntity(GettoneDto gettone){
//        return new Gettone(gettone.getId(), gettone.getIdFantagazzetta(), gettone.getNome(),gettone.getRuolo(), gettone.getQuotazione());
//    }
}
