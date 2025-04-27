package it.fantacalcio.ffm.domain.model.fantaleghe;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AcquistoGettoni {
    private Integer gettoni;
    private Integer idSquadra;
    private LocalDateTime dataAcquisto;
}