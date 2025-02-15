package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.domain.dto.TrattativaDto;
import it.fantacalcio.ffm.domain.entity.Trattativa;
import org.springframework.stereotype.Component;

@Component
public class TrattativaConverter {
    private TrattativaConverter() {}

    public static TrattativaDto toDto(Trattativa trattativa){
        return new TrattativaDto(trattativa.getId(),
                StagioneConverter.toDto(trattativa.getIdStagione()),
                trattativa.getData());
    }

    public static Trattativa toEntity(TrattativaDto trattativa){
        Trattativa trattativaEntity = new Trattativa();
        trattativaEntity.setId(trattativa.getId());
        trattativaEntity.setIdStagione(StagioneConverter.toEntity(trattativa.getIdStagione()));
        trattativaEntity.setData(trattativa.getData());
        return trattativaEntity;
    }
}
