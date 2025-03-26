package it.fantacalcio.ffm.batch.utility;

import it.fantacalcio.ffm.batch.model.EsitoRisultato;
import it.fantacalcio.ffm.utility.Constants;

import java.util.Arrays;

import static it.fantacalcio.ffm.utility.Constants.SEPARATORE_RISULTATO;

public class RisultatoCompetizioneHelper {
    public static EsitoRisultato calcolaEsitoRisultato(String risultato) {
        int[] goals = Arrays.stream(risultato.split(SEPARATORE_RISULTATO))
                .mapToInt(Integer::parseInt)
                .toArray();

        int goalsSquadraA = goals[0];
        int goalsSquadraB = goals[1];
        int bonusSquadraA = calcolaBonus(goalsSquadraA);
        int bonusSquadraB = calcolaBonus(goalsSquadraB);

        Constants.SquadraOwnerEnum squadraVincitrice = calcolaSquadraVincitrice(goalsSquadraA, goalsSquadraB);

        return new EsitoRisultato(bonusSquadraA, bonusSquadraB, squadraVincitrice);
    }

    private static int calcolaBonus(int goals) {
        if (goals >= 5) {
            return 2;
        } else if (goals == 4) {
            return 1;
        } else {
            return 0;
        }
    }

    private static Constants.SquadraOwnerEnum calcolaSquadraVincitrice(int risultatoSquadraA, int risultatoSquadraB){
        Constants.SquadraOwnerEnum squadraVincitrice = null;
        if (risultatoSquadraA > risultatoSquadraB) {
            squadraVincitrice = Constants.SquadraOwnerEnum.SQUADRA_A;
        } else if (risultatoSquadraB > risultatoSquadraA) {
            squadraVincitrice = Constants.SquadraOwnerEnum.SQUADRA_B;
        }
        return squadraVincitrice;
    }
}
