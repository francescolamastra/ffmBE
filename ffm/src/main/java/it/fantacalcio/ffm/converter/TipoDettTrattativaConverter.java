package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.domain.dto.TipoDettTrattativaDto;
import it.fantacalcio.ffm.domain.entity.TipoDettTrattativa;
import org.springframework.stereotype.Component;

@Component
public class TipoDettTrattativaConverter {
    private TipoDettTrattativaConverter() {}

    public static TipoDettTrattativaDto toDto(TipoDettTrattativa tipoDettTrattativa){
        return new TipoDettTrattativaDto(tipoDettTrattativa.getId(),
                tipoDettTrattativa.getSigla(),
                tipoDettTrattativa.getDescrizione());
    }

    public static TipoDettTrattativa toEntity(TipoDettTrattativaDto tipoDettTrattativa){
        TipoDettTrattativa tipoDettTrattativaEntity = new TipoDettTrattativa();
        tipoDettTrattativaEntity.setId(tipoDettTrattativa.getId());
        tipoDettTrattativaEntity.setSigla(tipoDettTrattativa.getSigla());
        tipoDettTrattativaEntity.setDescrizione(tipoDettTrattativa.getDescrizione());
        return tipoDettTrattativaEntity;
    }
}
