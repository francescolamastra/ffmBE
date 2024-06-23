package it.fantacalcio.ffm.domain.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link it.fantacalcio.ffm.domain.entity.Credenziali}
 */
@Value
public class CredenzialiDto implements Serializable {
    Integer id;
    UtenteDto idUtente;
    String userName;
    String password;
}