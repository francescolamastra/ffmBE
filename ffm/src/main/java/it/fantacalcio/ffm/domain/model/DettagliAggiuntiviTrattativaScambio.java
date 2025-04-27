package it.fantacalcio.ffm.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DettagliAggiuntiviTrattativaScambio {
    private Integer bonusPostSquadraA;
    private Integer bonusPostSquadraB;
    private Integer creditiPostSquadraA;
    private Integer creditiPostSquadraB;
    private Integer gettoniSquadraA;
    private Integer gettoniSquadraB;
    private Integer idSquadraA;
    private Integer idSquadraB;
    private Integer idTrattativa;
    private String clausole;
}