package it.fantacalcio.ffm.batch.config.drive.sheet;

public interface SheetConfig {
    String[] getColumns();
    int linesToSkip();
    int linesToRead();
}
