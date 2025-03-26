package it.fantacalcio.ffm.batch.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class RisultatoCompetizioneBatchRecord implements Serializable {
    String squadraA;
    String squadraB;
    String risultato;
    Integer giornataCompetizione;
    Integer giornataSerieA;
}
