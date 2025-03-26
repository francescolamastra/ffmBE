package it.fantacalcio.ffm.batch.model;

import it.fantacalcio.ffm.utility.Constants;
import lombok.Value;

@Value
public class EsitoRisultato {
    int bonusSquadraA;
    int bonusSquadraB;
    Constants.SquadraOwnerEnum squadraVincitrice;
}
