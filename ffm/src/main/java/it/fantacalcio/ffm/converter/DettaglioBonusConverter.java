package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.domain.dto.DettaglioBonusDto;
import it.fantacalcio.ffm.domain.entity.DettaglioBonus;

public class DettaglioBonusConverter {
    private DettaglioBonusConverter() {}

    public static DettaglioBonusDto toDto(DettaglioBonus dettaglioBonus){
        return new DettaglioBonusDto(dettaglioBonus.getId(),
                dettaglioBonus.getSigla(),
                dettaglioBonus.getDescrizione());
    }

    public static DettaglioBonus toEntity(DettaglioBonusDto dettaglioBonus){
        DettaglioBonus dettaglioBonusEntity = new DettaglioBonus();
        dettaglioBonusEntity.setId(dettaglioBonus.getId());
        dettaglioBonusEntity.setSigla(dettaglioBonus.getSigla());
        dettaglioBonusEntity.setDescrizione(dettaglioBonus.getDescrizione());
        return dettaglioBonusEntity;
    }
}
