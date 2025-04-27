package it.fantacalcio.ffm.domain.dto;

import it.fantacalcio.ffm.domain.entity.Trattativa;
import it.fantacalcio.ffm.utility.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link Trattativa}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrattativaDto implements Serializable {
    Integer id;
    StagioneDto idStagione;
    String clausole;
    Constants.SessioneMercatoTrattiveScambioEnum sessioneMercato;
    LocalDateTime dataCreazione;
}