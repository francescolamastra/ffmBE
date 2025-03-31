package it.fantacalcio.ffm.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link it.fantacalcio.ffm.domain.entity.TokenCredenziali}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenCredenzialiDto implements Serializable {
    Integer id;
    String jwt;
    UtenteDto utente;
    NazioneDto nazione;
    Boolean isValid;
    LocalDateTime dataCreazione;
}