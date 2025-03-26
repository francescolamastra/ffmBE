package it.fantacalcio.ffm.utility;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.regex.Pattern;

public class Constants {

    // Esempio di costanti
    public static final String UPLOADS_DIR = "uploads";
    public static final int ANNI_CONTRATTO_DEFAULT = 3;
    public static final String SEPARATORE_RISULTATO = "-";
    public static final Pattern PATTERN_SQUADRA_JOINED_STRING = Pattern.compile("^(.*) (\\S+) (\\S+)$");
    public static final Integer DEFAULT_ALL = 9999;
    public static final Integer TOT_SQUADRE_CAT_A = 20;
    public static final Integer TOT_SQUADRE_CAT_B_C = 16;

    // Enumerazione per TipoOperazioneEnum
    @Getter
    @RequiredArgsConstructor
    public enum TipoOperazioneEnum {
        ACQUISTO("A"),
        CESSIONE("C"),
        SVINCOLO("S");

        private final String sigla;
    }

    @Getter
    @RequiredArgsConstructor
    public enum SegnoEnum {
        CREDITO("C"),
        DEBITO("D");

        private final String sigla;
    }

    @Getter
    @RequiredArgsConstructor
    public enum SquadraOwnerEnum {
        SQUADRA_A("A"),
        SQUADRA_B("B");

        private final String sigla;
    }

    @Getter
    @RequiredArgsConstructor
    public enum TipoDettTrattativaEnum {
        PRESTITO("P"),
        DEFINITIVO("D");

        private final String sigla;
    }

    @Getter
    @RequiredArgsConstructor
    public enum RisultatoCompetizioneEnum {
        VITTORIA("V"),
        PAREGGIO("P"),
        SCONFITTA("S");

        private final String sigla;
    }

    @Getter
    @RequiredArgsConstructor
    public enum StadioCompetizioneEnum {
        CASA("C"),
        TRASFERTA("T"),
        NEUTRO("N");

        private final String sigla;
    }

    @Getter
    @RequiredArgsConstructor
    public enum SessioneMercatoOpAcquistoEnum {
        PREASTA("PRE"),
        INIZIALE("INI"),
        AGOSTO("AGO"),
        SETTEMBRE("SET"),
        FEBBRAIO("FEB");

        private final String sigla;
        public static SessioneMercatoOpAcquistoEnum fromValue(String sigla) {
            for (SessioneMercatoOpAcquistoEnum sessione : values()) {
                if (sessione.sigla.equalsIgnoreCase(sigla)) {
                    return sessione;
                }
            }
            throw new IllegalArgumentException("Valore non valido per SessioneMercatoOpAcquistoEnum: " + sigla);
        }
    }

    @Getter
    @RequiredArgsConstructor
    public enum CompetizioneEnum {
        CAMPIONATO("C"),
        COPPA_DI_LEGA("CDL"),
        CHAMPIONS_LEAGUE("CL"),
        EUROPA_LEAGUE("EL"),
        INTERNATIONAL("INT"),
        CONFERENCE_LEAGUE("CONF"),
        COPPA_CAMPIONI("CC"),
        SUPERCOPPA_DI_LEGA("SCDL"),
        SUPERCOPPA_EUROPEA("SE");

        private final String sigla;
        public static CompetizioneEnum fromValue(String sigla) {
            for (CompetizioneEnum competizioneEnum : values()) {
                if (competizioneEnum.sigla.equalsIgnoreCase(sigla)) {
                    return competizioneEnum;
                }
            }
            throw new IllegalArgumentException("Valore non valido per CompetizioneEnum: " + sigla);
        }
    }

    @Getter
    @RequiredArgsConstructor
    public enum FaseCompetizioneEnum {
        PRELIMINARE_1("P1"),
        PRELIMINARE_2("P2"),
        SESSANTAQUATTRESIMI("F64"),
        TRENTADUESIMI("F32"),
        SEDICESIMI("F16"),
        OTTAVI("F8"),
        QUARTI("F4"),
        SEMIFINALI("FS"),
        FINALE("F"),
        REGOLARE("R");

        private final String sigla;
        public static FaseCompetizioneEnum fromValue(String sigla) {
            for (FaseCompetizioneEnum faseCompetizioneEnum : values()) {
                if (faseCompetizioneEnum.sigla.equalsIgnoreCase(sigla)) {
                    return faseCompetizioneEnum;
                }
            }
            throw new IllegalArgumentException("Valore non valido per CompetizioneEnum: " + sigla);
        }
    }
}
