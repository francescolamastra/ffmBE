package it.fantacalcio.ffm.domain.dto;

import it.fantacalcio.ffm.domain.entity.DettaglioTrattativa;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link DettaglioTrattativa}
 */
@Value
public class DettaglioTrattativaDto implements Serializable {
    Integer id;
    TrattativaDto idTrattativa;
    TipoDettTrattativaDto idTipoDettTrattativa;
    SquadraDto idSquadra;
    GiocatoreDto idGiocatore;
    TipoOperazioneDto idTipoOperazione;
}