package it.fantacalcio.ffm.domain.dto;

import lombok.Value;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link it.fantacalcio.ffm.domain.entity.Gettone}
 */
@Value
public class GettoneDto implements Serializable {
    Integer id;
    SquadraDto idSquadra;
    Integer quantita;
    Instant dataAcquisto;
}