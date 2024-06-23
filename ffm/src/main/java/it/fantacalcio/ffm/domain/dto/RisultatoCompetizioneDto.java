package it.fantacalcio.ffm.domain.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link it.fantacalcio.ffm.domain.entity.RisultatoCompetizione}
 */
@Value
public class RisultatoCompetizioneDto implements Serializable {
    Integer id;
    StagioneCompetizioneDto idStagioneCompetizione;
    SquadraDto idSquadra;
    String risultato;
    Integer giornataSerieA;
    Integer giornataCompetizione;
    String luogoRisultato;
    FaseCompetizioneDto idFaseCompetizione;
}