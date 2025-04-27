package it.fantacalcio.ffm.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SituazioneEconomicaInizialeDto {
    private Integer id = null;
    private StagioneDto stagione;
    private SquadraDto squadra;
    private StadioDto stadio;
    private Integer crediti = 0;
    private Integer gettoni = 0;
}