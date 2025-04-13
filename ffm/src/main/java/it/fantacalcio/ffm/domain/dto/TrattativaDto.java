package it.fantacalcio.ffm.domain.dto;

import it.fantacalcio.ffm.domain.entity.Trattativa;
import it.fantacalcio.ffm.utility.Constants;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link Trattativa}
 */
@Value
public class TrattativaDto implements Serializable {
    Integer id;
    StagioneDto idStagione;
    String clausole;
    Constants.SessioneMercatoTrattiveScambioEnum sessioneMercato;
    LocalDateTime dataCreazione;
}