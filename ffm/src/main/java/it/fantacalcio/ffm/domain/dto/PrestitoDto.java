package it.fantacalcio.ffm.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * DTO for {@link it.fantacalcio.ffm.domain.entity.Prestito}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrestitoDto implements Serializable {
    Integer id;
    DettaglioTrattativaDto idDettTrattativa;
    Integer costoRiscatto;
    Boolean obbligo;
    Boolean riscatto;
    Boolean esercitato;
}