package it.fantacalcio.ffm.batch.model;

import it.fantacalcio.ffm.domain.dto.GiocatoreRosaDto;
import it.fantacalcio.ffm.domain.dto.OperazioneDto;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class GiocatoreRosaOperazioneComposite {
    private List<GiocatoreRosaDto> giocatoreRosaDtoList;
    private List<OperazioneDto> operazioneDtoList;
}
