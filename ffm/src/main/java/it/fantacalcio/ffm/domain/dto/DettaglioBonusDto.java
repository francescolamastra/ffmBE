package it.fantacalcio.ffm.domain.dto;

import it.fantacalcio.ffm.domain.entity.DettaglioBonus;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link DettaglioBonus}
 */
@Value
public class DettaglioBonusDto implements Serializable {
    Integer id;
    String sigla;
    String descrizione;
}