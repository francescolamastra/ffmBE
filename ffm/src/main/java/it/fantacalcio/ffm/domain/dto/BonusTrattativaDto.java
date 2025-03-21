package it.fantacalcio.ffm.domain.dto;

import it.fantacalcio.ffm.domain.entity.Bonus;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link Bonus}
 */
@Value
public class BonusTrattativaDto implements Serializable {
    Integer id;
    TrattativaDto idTrattativa;
    SquadraDto squadra;
    Integer massimale;
    String segnoBonus;
}