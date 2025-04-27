package it.fantacalcio.ffm.domain.dto;

import it.fantacalcio.ffm.domain.entity.GiocatoreListone;
import it.fantacalcio.ffm.utility.Constants;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link GiocatoreListone}
 */
public record GiocatoreListoneDto(Integer id,
                                  Integer idFantagazzetta,
                                  StagioneDto idStagione,
                                  Integer fvm,
                                  Constants.TipologiaListoneEnum tipologiaListone,
                                  LocalDateTime dataCreazione) implements Serializable {
}