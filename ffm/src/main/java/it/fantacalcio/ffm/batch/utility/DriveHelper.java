package it.fantacalcio.ffm.batch.utility;

import it.fantacalcio.ffm.batch.config.drive.sheet.ListoneSheetConfig;
import it.fantacalcio.ffm.batch.config.drive.sheet.SheetConfig;
import it.fantacalcio.ffm.batch.config.drive.sheet.SquadraSheetConfig;
import it.fantacalcio.ffm.utility.Constants;

public class DriveHelper {
    public static SheetConfig getSheetConfig(Constants.TipoSheet tipoSheet) {
        return switch (tipoSheet) {
            case SQUADRA -> new SquadraSheetConfig();
            case LISTONE -> new ListoneSheetConfig();
        };
    }
}
