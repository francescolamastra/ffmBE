package it.fantacalcio.ffm.domain.dto;

import it.fantacalcio.ffm.domain.entity.DettaglioTrattativa;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * DTO for {@link DettaglioTrattativa}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DettaglioTrattativaDto implements Serializable {
    Integer id;
    TrattativaDto idTrattativa;
    TipoDettTrattativaDto idTipoDettTrattativa;
    SquadraDto idSquadra;
    GiocatoreDto idGiocatore;
    TipoOperazioneDto idTipoOperazione;
    PrestitoDto idPrestito;
}