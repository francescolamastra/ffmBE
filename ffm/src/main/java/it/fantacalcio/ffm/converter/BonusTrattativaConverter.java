package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.domain.dto.BonusTrattativaDto;
import it.fantacalcio.ffm.domain.entity.BonusTrattativa;

public class BonusTrattativaConverter {
    private BonusTrattativaConverter() {}

    public static BonusTrattativaDto toDto(BonusTrattativa bonus){
        return new BonusTrattativaDto(bonus.getId(),
                TrattativaConverter.toDto(bonus.getTrattativa()),
                SquadraConverter.toDto(bonus.getIdSquadra()),
                bonus.getMassimale(),
                bonus.getSegnoBonus());
    }

    public static BonusTrattativa toEntity(BonusTrattativaDto bonus){
        BonusTrattativa bonusEntity = new BonusTrattativa();
        bonusEntity.setId(bonus.getId());
        bonusEntity.setSegnoBonus(bonus.getSegnoBonus());
        bonusEntity.setMassimale(bonus.getMassimale());
        bonusEntity.setTrattativa(TrattativaConverter.toEntity(bonus.getIdTrattativa()));
        bonusEntity.setIdSquadra(SquadraConverter.toEntity(bonus.getSquadra()));
        return bonusEntity;
    }
}
