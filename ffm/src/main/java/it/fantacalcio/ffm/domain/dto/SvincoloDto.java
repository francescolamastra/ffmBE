package it.fantacalcio.ffm.domain.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link it.fantacalcio.ffm.domain.entity.Svincolo}
 */
@Value
public class SvincoloDto implements Serializable {
    Integer id;
    OperazioneDto idOperazione;
    Integer percentuale;
}