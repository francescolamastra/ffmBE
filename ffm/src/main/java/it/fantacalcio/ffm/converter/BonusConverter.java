package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.domain.dto.BonusDto;
import it.fantacalcio.ffm.domain.entity.Bonus;
import org.springframework.stereotype.Component;

@Component
public class BonusConverter {
    private BonusConverter() {}

    public static BonusDto toDto(Bonus bonus){
        return new BonusDto(bonus.getId(),DettaglioTrattativaConverter.toDto(bonus.getIdDettTrattativa()), DettaglioBonusConverter.toDto(bonus.getIdDettBonus()), bonus.getMassimale(),bonus.getImportoSingolo(), bonus.getTipoBonus());
    }

//    public static Bonus toEntity(BonusDto bonus){
//        return new Bonus(bonus.getId(), bonus.getIdFantagazzetta(), bonus.getNome(),bonus.getRuolo(), bonus.getQuotazione());
//    }
}
