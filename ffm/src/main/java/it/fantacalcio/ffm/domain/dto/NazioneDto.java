package it.fantacalcio.ffm.domain.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link it.fantacalcio.ffm.domain.entity.Nazione}
 */
@Value
public class NazioneDto implements Serializable {
    Integer id;
    String descrizione;
    String sigla;
}