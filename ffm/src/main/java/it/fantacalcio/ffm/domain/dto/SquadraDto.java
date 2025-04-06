package it.fantacalcio.ffm.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link it.fantacalcio.ffm.domain.entity.Squadra}
 */
@Data
@AllArgsConstructor
public class SquadraDto implements Serializable {
    Integer id;
    Integer idFantagazzetta;
    NazioneDto idNazione;
    CategoriaDto idCategoria;
    String nome;
    StadioDto idStadio;
    LocalDateTime dataCreazione;
}