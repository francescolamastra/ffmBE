package it.fantacalcio.ffm.domain.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link it.fantacalcio.ffm.domain.entity.StagioneCompetizione}
 */
@Value
public class StagioneCompetizioneDto implements Serializable {
    Integer id;
    StagioneDto idStagione;
    CompetizioneDto idCompetizione;
}