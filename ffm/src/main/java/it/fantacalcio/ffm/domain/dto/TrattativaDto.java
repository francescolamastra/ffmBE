package it.fantacalcio.ffm.domain.dto;

import it.fantacalcio.ffm.domain.entity.Trattativa;
import lombok.Value;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link Trattativa}
 */
@Value
public class TrattativaDto implements Serializable {
    Integer id;
    StagioneDto idStagione;
    Instant data;
}