package it.fantacalcio.ffm.domain.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link it.fantacalcio.ffm.domain.entity.Stadio}
 */
@Value
public class StadioDto implements Serializable {
    Integer id;
    Integer livello;
    Float coefficiente;
    Integer compensoVittoria;
    Integer compensoPareggio;
    Float bonus;
    Integer manutenzione;
}