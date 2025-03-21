package it.fantacalcio.ffm.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import it.fantacalcio.ffm.utility.CustomDateDeserializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link it.fantacalcio.ffm.domain.entity.Operazione}
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OperazioneDto implements Serializable {
    Integer id;
    SquadraDto idSquadra;
    GiocatoreDto idGiocatore;
    TipoOperazioneDto idTipoOperazione;
    StagioneDto idStagione;
    TransazioneOperazioneDto transazione;
    AcquistoDto acquisto;
    SvincoloDto svincolo;
    @JsonDeserialize(using = CustomDateDeserializer.class)
    LocalDateTime dataCreazione;
}