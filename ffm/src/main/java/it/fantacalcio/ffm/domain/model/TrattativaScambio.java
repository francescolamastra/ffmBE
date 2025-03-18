package it.fantacalcio.ffm.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrattativaScambio {
    private BonusTrattativa bonusPostSquadraA;
    private BonusTrattativa bonusPostSquadraB;
    private int creditiPagatiSquadraA;
    private int creditiPagatiSquadraB;
    private int creditiPostSquadraA;
    private int creditiPostSquadraB;
    private String dataTrattativa;
    private int gettoniSquadraA;
    private int gettoniSquadraB;
    private int idSquadraA;
    private int idSquadraB;
    private List<GiocatoreTrattativa> listGiocatoriCedutiSquadraA;
    private List<GiocatoreTrattativa> listGiocatoriCedutiSquadraB;
}
