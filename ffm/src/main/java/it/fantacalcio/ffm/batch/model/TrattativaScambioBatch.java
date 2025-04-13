package it.fantacalcio.ffm.batch.model;

import it.fantacalcio.ffm.domain.dto.SquadraDto;
import it.fantacalcio.ffm.domain.model.GiocatoreTrattativaScambioComposite;
import it.fantacalcio.ffm.utility.Constants;
import lombok.Value;

import java.util.List;
import java.util.Map;

@Value
public class TrattativaScambioBatch {
    SquadraDto squadraDtoA;
    SquadraDto squadraDtoB;
    Constants.SessioneMercatoTrattiveScambioEnum sessioneMercatoTrattiveScambioEnum;
    Map<Constants.SquadraOwnerEnum, Integer> mapSquadraCreditiPagati;
    List<GiocatoreTrattativaScambioComposite> listGiocatoriScambiati;
}
