package it.fantacalcio.ffm.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link it.fantacalcio.ffm.domain.entity.TransazioneTrattativa}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransazioneTrattativaDto implements Serializable {
    Integer id;
    TrattativaDto idTrattativa;
    SquadraDto idSquadra;
    Integer importo;
    String segnoTransazione;
    Integer gettoniSpesi;
}