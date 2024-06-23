package it.fantacalcio.ffm.domain.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link it.fantacalcio.ffm.domain.entity.FaseCompetizione}
 */
@Value
public class FaseCompetizioneDto implements Serializable {
    Integer id;
    String sigla;
    String descrizione;
}