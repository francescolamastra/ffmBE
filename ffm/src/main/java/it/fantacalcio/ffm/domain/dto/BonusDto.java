package it.fantacalcio.ffm.domain.dto;

import it.fantacalcio.ffm.domain.entity.Bonus;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link Bonus}
 */
@Value
public class BonusDto implements Serializable {
    Integer id;
    DettaglioTrattativaDto idDettTrattativa;
    DettaglioBonusDto idDettBonus;
    Integer massimale;
    Integer importoSingolo;
    String tipoBonus;
}