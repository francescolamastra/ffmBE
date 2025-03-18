package it.fantacalcio.ffm.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrestitoTrattativa {
    private int costoRiscatto;
    private boolean obbligo;
    private boolean riscatto;
}