package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.domain.dto.GettoneDto;
import it.fantacalcio.ffm.domain.entity.Gettone;
import org.springframework.stereotype.Component;

@Component
public class GettoneConverter {
    private GettoneConverter() {}

    public static GettoneDto toDto(Gettone gettone){
        return new GettoneDto(gettone.getId(),
                SquadraConverter.toDto(gettone.getIdSquadra()),
                gettone.getQuantita(),gettone.getDataAcquisto());
    }

    public static Gettone toEntity(GettoneDto gettone){
        Gettone gettoneEntity = new Gettone();
        gettoneEntity.setId(gettone.getId());
        gettoneEntity.setQuantita(gettone.getQuantita());
        gettoneEntity.setIdSquadra(SquadraConverter.toEntity(gettone.getIdSquadra()));
        gettoneEntity.setDataAcquisto(gettone.getDataAcquisto());
        return gettoneEntity;
    }
}
