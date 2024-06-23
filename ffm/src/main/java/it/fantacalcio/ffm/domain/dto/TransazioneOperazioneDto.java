package it.fantacalcio.ffm.domain.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link it.fantacalcio.ffm.domain.entity.TransazioneOperazione}
 */
@Value
public class TransazioneOperazioneDto implements Serializable {
    Integer id;
    OperazioneDto idOperazione;
    Integer importo;
    String tipoTransazione;
}