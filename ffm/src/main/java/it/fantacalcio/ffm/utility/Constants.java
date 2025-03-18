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
        ACQUISTO("A", "Acquisto"),
        CESSIONE("C", "Cessione"),
        SVINCOLO("S", "Svincolo");

        private final String sigla;
        private final String descrizione;
    }

    @Getter
    @RequiredArgsConstructor
    public enum Segno {
        CREDITO("C", "Credito"),
        DEBITO("D", "Debito");

        private final String sigla;
        private final String descrizione;
    }
}
