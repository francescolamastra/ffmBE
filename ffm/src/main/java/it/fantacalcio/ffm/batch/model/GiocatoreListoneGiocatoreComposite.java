package it.fantacalcio.ffm.batch.model;

import it.fantacalcio.ffm.domain.dto.GiocatoreDto;
import it.fantacalcio.ffm.domain.dto.GiocatoreListoneDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class GiocatoreListoneGiocatoreComposite {
    private GiocatoreListoneDto giocatoreListoneDto;
    private GiocatoreDto giocatoreDto;
}
