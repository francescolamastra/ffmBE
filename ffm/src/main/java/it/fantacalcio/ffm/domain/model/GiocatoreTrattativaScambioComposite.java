package it.fantacalcio.ffm.domain.model;

import it.fantacalcio.ffm.domain.dto.GiocatoreDto;
import it.fantacalcio.ffm.domain.dto.TipoDettTrattativaDto;
import it.fantacalcio.ffm.domain.dto.TipoOperazioneDto;
import it.fantacalcio.ffm.utility.Constants;
import lombok.Value;

import java.util.Map;

@Value
public class GiocatoreTrattativaScambioComposite {
    GiocatoreDto giocatoreDto;
    TipoDettTrattativaDto tipoDettTrattativaDto;
    Map<Constants.SquadraOwnerEnum, TipoOperazioneDto> squadraTipoOperazione;
}
