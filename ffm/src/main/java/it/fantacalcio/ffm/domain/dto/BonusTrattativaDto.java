package it.fantacalcio.ffm.domain.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link it.fantacalcio.ffm.domain.entity.BonusTrattativa}
 */
@Value
public class BonusTrattativaDto implements Serializable {
    Integer id;
    TrattativaDto idTrattativa;
    SquadraDto squadra;
    Integer massimale;
    String segnoBonus;
}