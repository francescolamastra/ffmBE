package it.fantacalcio.ffm.domain.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link it.fantacalcio.ffm.domain.entity.Acquisto}
 */
@Value
public class AcquistoDto implements Serializable {
    Integer id;
    OperazioneDto idOperazione;
    Integer anniContratto;
}