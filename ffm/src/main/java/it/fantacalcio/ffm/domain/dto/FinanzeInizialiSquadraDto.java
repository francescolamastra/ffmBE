package it.fantacalcio.ffm.domain.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link it.fantacalcio.ffm.domain.entity.FinanzeInizialiSquadra}
 */
@Value
public class FinanzeInizialiSquadraDto implements Serializable {
    Integer id;
    StagioneDto idStagione;
    SquadraDto idSquadra;
    Integer importo;
}