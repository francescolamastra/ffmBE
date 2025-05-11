package it.fantacalcio.ffm.batch.config.drive.sheet;

public class ManagerialeSquadraSheetConfig extends SquadraSheetConfig{
    @Override
    public int linesToSkip() {
        return 455;
    }

    @Override
    public int linesToRead() {
        return 38;
    }
}
