package it.fantacalcio.ffm.domain.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link it.fantacalcio.ffm.domain.entity.Prestito}
 */
@Value
public class PrestitoDto implements Serializable {
    Integer id;
    OperazioneDto idOperazione;
    SquadraDto idSquadraCedente;
    Integer costoRiscatto;
    Boolean obbligo;
    Boolean riscatto;
    Boolean esercitato;
}