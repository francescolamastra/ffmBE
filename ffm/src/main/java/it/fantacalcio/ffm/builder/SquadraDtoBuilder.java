package it.fantacalcio.ffm.builder;

import it.fantacalcio.ffm.domain.dto.CategoriaDto;
import it.fantacalcio.ffm.domain.dto.NazioneDto;
import it.fantacalcio.ffm.domain.dto.SquadraDto;

import java.time.LocalDateTime;

public class SquadraDtoBuilder {
    private Integer id;
    private Integer idFantagazzetta;
    private String nome;
    private CategoriaDto idCategoria;
    private NazioneDto idNazione;
    private LocalDateTime dataCreazione;

    public SquadraDtoBuilder setId(Integer id) {
        this.id = id;
        return this;
    }

    public SquadraDtoBuilder setIdFantagazzetta(Integer idFantagazzetta) {
        this.idFantagazzetta = idFantagazzetta;
        return this;
    }

    public SquadraDtoBuilder setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public SquadraDtoBuilder setIdCategoria(CategoriaDto idCategoria) {
        this.idCategoria = idCategoria;
        return this;
    }

    public SquadraDtoBuilder setIdNazione(NazioneDto idNazione) {
        this.idNazione = idNazione;
        return this;
    }

    public SquadraDtoBuilder setDataCreazione(LocalDateTime dataCreazione) {
        this.dataCreazione = dataCreazione;
        return this;
    }

    public SquadraDto build() {
        return new SquadraDto(id, idFantagazzetta, idNazione, idCategoria, nome, dataCreazione);
    }
}