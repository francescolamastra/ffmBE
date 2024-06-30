package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.domain.dto.DettaglioBonusDto;
import it.fantacalcio.ffm.domain.entity.DettaglioBonus;
import org.springframework.stereotype.Component;

@Component
public class DettaglioBonusConverter {
    private DettaglioBonusConverter() {}

    public static DettaglioBonusDto toDto(DettaglioBonus dettaglioBonus){
        return new DettaglioBonusDto(dettaglioBonus.getId(), dettaglioBonus.getIdFantagazzetta(), dettaglioBonus.getNome(),dettaglioBonus.getRuolo(), dettaglioBonus.getQuotazione());
    }

//    public static DettaglioBonus toEntity(DettaglioBonusDto dettaglioBonus){
//        return new DettaglioBonus(dettaglioBonus.getId(), dettaglioBonus.getIdFantagazzetta(), dettaglioBonus.getNome(),dettaglioBonus.getRuolo(), dettaglioBonus.getQuotazione());
//    }
}
