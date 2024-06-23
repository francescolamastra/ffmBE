package it.fantacalcio.ffm.domain.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link it.fantacalcio.ffm.domain.entity.UtenteSquadra}
 */
@Value
public class UtenteSquadraDto implements Serializable {
    Integer id;
    UtenteDto idUtente;
    SquadraDto idSquadra;
}