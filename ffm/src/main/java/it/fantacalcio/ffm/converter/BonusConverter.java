package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.domain.dto.BonusDto;
import it.fantacalcio.ffm.domain.entity.Bonus;

public class BonusConverter {
    private BonusConverter() {}

    public static BonusDto toDto(Bonus bonus){
        return new BonusDto(bonus.getId(),
                DettaglioTrattativaConverter.toDto(bonus.getIdDettTrattativa()),
                DettaglioBonusConverter.toDto(bonus.getIdDettBonus()),
                bonus.getMassimale(),
                bonus.getImportoSingolo(),
                bonus.getSegnoBonus());
    }

    public static Bonus toEntity(BonusDto bonus){
        Bonus bonusEntity = new Bonus();
        bonusEntity.setId(bonus.getId());
        bonusEntity.setIdDettBonus(DettaglioBonusConverter.toEntity(bonus.getIdDettBonus()));
        bonusEntity.setSegnoBonus(bonus.getSegnoBonus());
        bonusEntity.setMassimale(bonus.getMassimale());
        bonusEntity.setImportoSingolo(bonus.getImportoSingolo());
        bonusEntity.setIdDettTrattativa(DettaglioTrattativaConverter.toEntity(bonus.getIdDettTrattativa()));
        return bonusEntity;
    }
}
