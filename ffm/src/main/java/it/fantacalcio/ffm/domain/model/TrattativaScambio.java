package it.fantacalcio.ffm.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrattativaScambio {
    private BonusTrattativaScambio bonusPostSquadraA;
    private BonusTrattativaScambio bonusPostSquadraB;
    private int creditiPagatiSquadraA;
    private int creditiPagatiSquadraB;
    private int creditiPostSquadraA;
    private int creditiPostSquadraB;
    private LocalDateTime dataTrattativa;
    private int gettoniSquadraA;
    private int gettoniSquadraB;
    private int idSquadraA;
    private int idSquadraB;
    private List<GiocatoreTrattativaScambio> listGiocatoriCedutiSquadraA;
    private List<GiocatoreTrattativaScambio> listGiocatoriCedutiSquadraB;
    private String clausole;
}
