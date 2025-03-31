package it.fantacalcio.ffm.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link it.fantacalcio.ffm.domain.entity.TokenCredenziali}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenCredenzialiProjectionDto implements Serializable {
    private String jwt;
    private NazioneDto nazione;
    private Boolean isValid;
}