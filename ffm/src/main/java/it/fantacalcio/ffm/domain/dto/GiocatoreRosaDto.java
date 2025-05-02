package it.fantacalcio.ffm.domain.dto;

import it.fantacalcio.ffm.domain.entity.GiocatoreRosa;
import it.fantacalcio.ffm.utility.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link GiocatoreRosa}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
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