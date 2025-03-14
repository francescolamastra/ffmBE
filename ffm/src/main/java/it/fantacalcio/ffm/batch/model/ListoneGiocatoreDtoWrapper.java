package it.fantacalcio.ffm.batch.model;

import it.fantacalcio.ffm.domain.dto.GiocatoreDto;
import it.fantacalcio.ffm.domain.dto.ListoneDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ListoneGiocatoreDtoWrapper {
    private ListoneDto listoneDto;
    private GiocatoreDto giocatoreDto;
}
