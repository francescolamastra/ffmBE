package it.fantacalcio.ffm.batch.model;

import it.fantacalcio.ffm.domain.entity.RisultatoCompetizione;
import lombok.Value;

@Value
public class MatchRecord {
    RisultatoCompetizione risultatoCompetizioneSquadraA;
    RisultatoCompetizione risultatoCompetizioneSquadraB;
}
