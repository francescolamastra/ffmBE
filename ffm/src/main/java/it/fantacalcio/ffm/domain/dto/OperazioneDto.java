package it.fantacalcio.ffm.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import it.fantacalcio.ffm.utility.CustomLocalDateTimeDeserializer;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

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
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    LocalDateTime dataCreazione;
}