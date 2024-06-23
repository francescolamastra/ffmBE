package it.fantacalcio.ffm.domain.dto;

import lombok.Value;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link it.fantacalcio.ffm.domain.entity.Operazione}
 */
@Value
public class OperazioneDto implements Serializable {
    Integer id;
    SquadraDto idSquadra;
    GiocatoreDto idGiocatore;
    TipoOperazioneDto idTipoOperazione;
    StagioneDto idStagione;
    Instant data;
}