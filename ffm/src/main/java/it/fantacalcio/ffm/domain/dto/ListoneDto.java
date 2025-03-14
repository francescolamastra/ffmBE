package it.fantacalcio.ffm.domain.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link it.fantacalcio.ffm.domain.entity.Giocatore}
 */
public record ListoneDto(Integer id,
                         Integer idFantagazzetta,
                         StagioneDto idStagione,
                         Integer fvm,
                         LocalDateTime dataCreazione) implements Serializable {
}