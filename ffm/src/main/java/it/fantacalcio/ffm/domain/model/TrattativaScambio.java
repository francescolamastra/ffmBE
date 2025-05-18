package it.fantacalcio.ffm.domain.model;

import it.fantacalcio.ffm.utility.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrattativaScambio {
    private Integer bonusPostSquadraA;
    private Integer bonusPostSquadraB;
    private Integer creditiPagatiSquadraA;
    private Integer creditiPagatiSquadraB;
    private Integer creditiPostSquadraA;
    private Integer creditiPostSquadraB;
    private LocalDateTime dataTrattativa;
    private Integer gettoniSquadraA;
    private Integer gettoniSquadraB;
    private Integer idSquadraA;
    private Integer idSquadraB;
    private List<GiocatoreTrattativaScambio> listGiocatoriCedutiSquadraA;
    private List<GiocatoreTrattativaScambio> listGiocatoriCedutiSquadraB;
    private String clausole;
    private Constants.SessioneMercatoTrattiveScambioEnum sessioneMercatoTrattiveScambioEnum;
}
