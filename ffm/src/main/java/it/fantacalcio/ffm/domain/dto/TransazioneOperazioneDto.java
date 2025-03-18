package it.fantacalcio.ffm.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * DTO for {@link it.fantacalcio.ffm.domain.entity.TransazioneOperazione}
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TransazioneOperazioneDto implements Serializable {
    Integer id;
    OperazioneDto idOperazione;
    Integer importo;
    String segnoTransazione;
}