package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.domain.dto.StagioneDto;
import it.fantacalcio.ffm.domain.entity.Stagione;
import org.springframework.stereotype.Component;

@Component
public class StagioneConverter {
    private StagioneConverter() {}

    public static StagioneDto toDto(Stagione stagione){
        return new StagioneDto(stagione.getId(),
                stagione.getAnnoInizio(),
                stagione.getAnnoFine());
    }

    public static Stagione toEntity(StagioneDto stagione){
        Stagione stagioneEntity = new Stagione();
        stagioneEntity.setId(stagione.getId());
        stagioneEntity.setAnnoInizio(stagione.getAnnoInizio());
        stagioneEntity.setAnnoFine(stagione.getAnnoFine());
        return stagioneEntity;
    }
}
