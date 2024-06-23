package it.fantacalcio.ffm.domain.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link it.fantacalcio.ffm.domain.entity.Utente}
 */
@Value
public class UtenteDto implements Serializable {
    Integer id;
    String nome;
    String cognome;
    String telefono;
    String email;
}