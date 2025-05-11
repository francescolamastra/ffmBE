package it.fantacalcio.ffm.batch.config.drive.sheet;

public class PreastaSquadraSheetConfig extends SquadraSheetConfig{
    @Override
    public int linesToSkip() {
        return 89;
    }

    @Override
    public int linesToRead() {
        return 28;
    }
}
