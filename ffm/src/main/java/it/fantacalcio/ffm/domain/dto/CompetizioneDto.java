package it.fantacalcio.ffm.domain.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link it.fantacalcio.ffm.domain.entity.Competizione}
 */
@Value
public class CompetizioneDto implements Serializable {
    Integer id;
    String sigla;
    String descrizione;
}