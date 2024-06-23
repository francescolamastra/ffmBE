package it.fantacalcio.ffm.domain.dto;

import lombok.Value;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link it.fantacalcio.ffm.domain.entity.Gettoni}
 */
@Value
public class GettoniDto implements Serializable {
    Integer id;
    SquadraDto idSquadra;
    Integer quantita;
    Instant dataAcquisto;
}