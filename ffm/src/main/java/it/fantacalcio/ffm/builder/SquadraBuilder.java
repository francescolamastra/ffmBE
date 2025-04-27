package it.fantacalcio.ffm.builder;

import it.fantacalcio.ffm.domain.entity.Categoria;
import it.fantacalcio.ffm.domain.entity.Nazione;
import it.fantacalcio.ffm.domain.entity.Squadra;

import java.time.LocalDateTime;

public class SquadraBuilder {
    private Integer id;
    private Integer idFantagazzetta;
    private String nome;
    private Categoria idCategoria;
    private Nazione idNazione;
    private LocalDateTime dataCreazione;

    public SquadraBuilder setId(Integer id) {
        this.id = id;
        return this;
    }

    public SquadraBuilder setIdFantagazzetta(Integer idFantagazzetta) {
        this.idFantagazzetta = idFantagazzetta;
        return this;
    }

    public SquadraBuilder setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public SquadraBuilder setIdCategoria(Categoria idCategoria) {
        this.idCategoria = idCategoria;
        return this;
    }

    public SquadraBuilder setIdNazione(Nazione idNazione) {
        this.idNazione = idNazione;
        return this;
    }

    public SquadraBuilder setDataCreazione(LocalDateTime dataCreazione) {
        this.dataCreazione = dataCreazione;
        return this;
    }

    public Squadra build() {
        return new Squadra(id,idFantagazzetta ,idNazione, idCategoria, nome, dataCreazione);
    }
}