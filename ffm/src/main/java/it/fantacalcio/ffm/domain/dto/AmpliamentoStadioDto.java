package it.fantacalcio.ffm.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AmpliamentoStadioDto {
    private Integer id = null;
    private StagioneDto stagione;
    private SquadraDto squadra;
    private StadioDto stadio;
    private Integer costo = 0;
}