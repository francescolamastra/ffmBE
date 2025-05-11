package it.fantacalcio.ffm.batch.config.drive.sheet;

public class PostListoneSquadraSheetConfig extends SquadraSheetConfig{
    @Override
    public int linesToSkip() {
        return 400;
    }

    @Override
    public int linesToRead() {
        return 38;
    }
}
