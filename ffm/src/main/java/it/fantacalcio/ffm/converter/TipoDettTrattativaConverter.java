package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.domain.dto.TipoDettTrattativaDto;
import it.fantacalcio.ffm.domain.entity.TipoDettTrattativa;
import org.springframework.stereotype.Component;

@Component
public class TipoDettTrattativaConverter {
    private TipoDettTrattativaConverter() {}

    public static TipoDettTrattativaDto toDto(TipoDettTrattativa tipoDettTrattativa){
        return new TipoDettTrattativaDto(tipoDettTrattativa.getId(), tipoDettTrattativa.getIdFantagazzetta(), tipoDettTrattativa.getNome(),tipoDettTrattativa.getRuolo(), tipoDettTrattativa.getQuotazione());
    }

//    public static TipoDettTrattativa toEntity(TipoDettTrattativaDto tipoDettTrattativa){
//        return new TipoDettTrattativa(tipoDettTrattativa.getId(), tipoDettTrattativa.getIdFantagazzetta(), tipoDettTrattativa.getNome(),tipoDettTrattativa.getRuolo(), tipoDettTrattativa.getQuotazione());
//    }
}
