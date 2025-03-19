package it.fantacalcio.ffm.domain.dto;

import it.fantacalcio.ffm.domain.entity.TipoDettTrattativa;
import it.fantacalcio.ffm.utility.Constants;
import lombok.Value;

import java.io.Serializable;
import java.util.Arrays;

/**
 * DTO for {@link TipoDettTrattativa}
 */
@Value
public class TipoDettTrattativaDto implements Serializable {
    Integer id;
    String sigla;
    String descrizione;

    public boolean isPrestito(){
        return Arrays.stream(Constants.TipoDettTrattativa.values())
                .anyMatch(it -> it.getSigla().equalsIgnoreCase(sigla) && it.equals(Constants.TipoDettTrattativa.PRESTITO));
    }
}