package it.fantacalcio.ffm.batch.utility;

import it.fantacalcio.ffm.batch.config.drive.sheet.*;
import it.fantacalcio.ffm.utility.Constants;

public class DriveHelper {
    public static SheetConfig getInstance(Constants.TipoSheet tipoSheet, Constants.TipologiaRosaEnum tipologiaRosaEnum) {
        return switch (tipoSheet) {
            case SQUADRA -> getSquadraSheetConfig(tipologiaRosaEnum);
            case LISTONE -> new ListoneSheetConfig();
        };
    }

    private static SheetConfig getSquadraSheetConfig(Constants.TipologiaRosaEnum tipologiaRosaEnum) {
        return switch (tipologiaRosaEnum) {
            case PREASTA -> new PreastaSquadraSheetConfig();
            case POST_LISTONE -> new PostListoneSquadraSheetConfig();
            case MANAGERIALE -> new ManagerialeSquadraSheetConfig();
            default -> throw new IllegalArgumentException("Tipologia Rosa non valida! " + tipologiaRosaEnum);
        };
    }
}
