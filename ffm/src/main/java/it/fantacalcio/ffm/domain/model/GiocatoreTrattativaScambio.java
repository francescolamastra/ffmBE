package it.fantacalcio.ffm.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GiocatoreTrattativaScambio {
    private int idFantagazzetta;
    private PrestitoTrattativaScambio prestito;
    private String tipoCessione;
}
