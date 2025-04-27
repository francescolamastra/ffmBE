package it.fantacalcio.ffm.domain.dto;

import it.fantacalcio.ffm.domain.entity.GiocatoreRosa;
import it.fantacalcio.ffm.utility.Constants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * DTO for {@link GiocatoreRosa}
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GiocatoreRosaDto implements Serializable {
    Integer id;
    SquadraDto idSquadra;
    GiocatoreDto idGiocatore;
    StagioneDto idStagione;
    Constants.TipologiaRosaEnum tipologiaRosa;
    Integer anniContratto;
    Integer costoAcquisto;
    Integer fvm;
}