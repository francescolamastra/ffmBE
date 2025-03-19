package it.fantacalcio.ffm.utility;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class Constants {

    // Esempio di costanti
    public static final String APP_NAME = "Fantacalcio";
    public static final int ANNI_CONTRATTO_DEFAULT = 3;

    // Enumerazione per TipoOperazione
    @Getter
    @RequiredArgsConstructor
    public enum TipoOperazione {
        ACQUISTO("A"),
        CESSIONE("C"),
        SVINCOLO("S");

        private final String sigla;
    }

    @Getter
    @RequiredArgsConstructor
    public enum Segno {
        CREDITO("C"),
        DEBITO("D");

        private final String sigla;
    }

    @Getter
    @RequiredArgsConstructor
    public enum SquadraCedente {
        SQUADRA_A("A"),
        SQUADRA_B("B");

        private final String sigla;
    }

    @Getter
    @RequiredArgsConstructor
    public enum TipoDettTrattativa {
        PRESTITO("P"),
        DEFINITIVO("D");

        private final String sigla;
    }
}
