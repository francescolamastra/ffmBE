package it.fantacalcio.ffm.domain.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link it.fantacalcio.ffm.domain.entity.Fido}
 */
@Value
public class FidoDto implements Serializable {
    Integer id;
    TrattativaDto idTrattativa;
    SquadraDto idSquadra;
    Integer importo;
    String tipoFido;
}