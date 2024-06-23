package it.fantacalcio.ffm.domain.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link it.fantacalcio.ffm.domain.entity.Squadra}
 */
@Value
public class SquadraDto implements Serializable {
    Integer id;
    NazioneDto idNazione;
    CategoriaDto idCategoria;
    String nome;
    StadioDto idStadio;
}