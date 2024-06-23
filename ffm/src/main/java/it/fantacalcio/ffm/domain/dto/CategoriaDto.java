package it.fantacalcio.ffm.domain.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link it.fantacalcio.ffm.domain.entity.Categoria}
 */
@Value
public class CategoriaDto implements Serializable {
    Integer id;
    String descrizione;
    String sigla;
}