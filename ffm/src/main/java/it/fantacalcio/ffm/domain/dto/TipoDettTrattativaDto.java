package it.fantacalcio.ffm.domain.dto;

import it.fantacalcio.ffm.domain.entity.TipoDettTrattativa;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link TipoDettTrattativa}
 */
@Value
public class TipoDettTrattativaDto implements Serializable {
    Integer id;
    String sigla;
    String descrizione;
}