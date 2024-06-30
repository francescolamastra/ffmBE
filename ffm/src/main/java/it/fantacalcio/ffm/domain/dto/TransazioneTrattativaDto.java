package it.fantacalcio.ffm.domain.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link it.fantacalcio.ffm.domain.entity.TransazioneTrattativa}
 */
@Value
public class TransazioneTrattativaDto implements Serializable {
    Integer id;
    TrattativaDto idTrattativa;
    SquadraDto idSquadra;
    Integer importo;
    String tipoTransazione;
    Integer gettoniSpesi;
}