package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.domain.dto.SvincoloDto;
import it.fantacalcio.ffm.domain.entity.Svincolo;
import org.springframework.stereotype.Component;

@Component
public class SvincoloConverter {
    private SvincoloConverter() {}

    public static SvincoloDto toDto(Svincolo svincolo){
        return new SvincoloDto(svincolo.getId(), svincolo.getIdFantagazzetta(), svincolo.getNome(),svincolo.getRuolo(), svincolo.getQuotazione());
    }

//    public static Svincolo toEntity(SvincoloDto svincolo){
//        return new Svincolo(svincolo.getId(), svincolo.getIdFantagazzetta(), svincolo.getNome(),svincolo.getRuolo(), svincolo.getQuotazione());
//    }
}
