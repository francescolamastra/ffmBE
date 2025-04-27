package it.fantacalcio.ffm.builder;

import it.fantacalcio.ffm.domain.dto.SituazioneEconomicaInizialeDto;
import it.fantacalcio.ffm.domain.dto.SquadraDto;
import it.fantacalcio.ffm.domain.dto.StadioDto;
import it.fantacalcio.ffm.domain.dto.StagioneDto;

public class SituazioneEconomicaInizialeDtoBuilder {
    private Integer id = null;
    private StagioneDto stagione;
    private SquadraDto squadra;
    private StadioDto stadio;
    private Integer crediti;
    private Integer gettoni;

    public SituazioneEconomicaInizialeDtoBuilder setId(Integer id) {
        this.id = id;
        return this;
    }

    public SituazioneEconomicaInizialeDtoBuilder setStagione(StagioneDto stagione) {
        this.stagione = stagione;
        return this;
    }

    public SituazioneEconomicaInizialeDtoBuilder setSquadra(SquadraDto squadra) {
        this.squadra = squadra;
        return this;
    }

    public SituazioneEconomicaInizialeDtoBuilder setStadio(StadioDto stadio) {
        this.stadio = stadio;
        return this;
    }

    public SituazioneEconomicaInizialeDtoBuilder setCrediti(Integer crediti) {
        this.crediti = crediti;
        return this;
    }

    public SituazioneEconomicaInizialeDtoBuilder setGettoni(Integer gettoni) {
        this.gettoni = gettoni;
        return this;
    }

    public SituazioneEconomicaInizialeDto build() {
        return new SituazioneEconomicaInizialeDto(id,stagione ,squadra, stadio, crediti, gettoni);
    }
}
