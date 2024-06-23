package it.fantacalcio.ffm.domain.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link it.fantacalcio.ffm.domain.entity.Stagione}
 */
@Value
public class StagioneDto implements Serializable {
    Integer id;
    Integer annoInizio;
    Integer annoFine;
}