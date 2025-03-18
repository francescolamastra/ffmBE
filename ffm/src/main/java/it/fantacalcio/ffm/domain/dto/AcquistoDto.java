package it.fantacalcio.ffm.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * DTO for {@link it.fantacalcio.ffm.domain.entity.Acquisto}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AcquistoDto implements Serializable {
    Integer id;
    OperazioneDto idOperazione;
    Integer anniContratto;
}