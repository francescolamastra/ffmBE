package it.fantacalcio.ffm.domain.dto;

import java.io.Serializable;

/**
 * DTO for {@link it.fantacalcio.ffm.domain.entity.Giocatore}
 */
public record GiocatoreDto(Integer id, Integer idFantagazzetta, String nome, String ruolo,
                           Integer quotazione) implements Serializable {
}