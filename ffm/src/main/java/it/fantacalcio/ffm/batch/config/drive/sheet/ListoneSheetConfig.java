package it.fantacalcio.ffm.batch.config.drive.sheet;

public class ListoneSheetConfig implements SheetConfig {
    @Override
    public String[] getColumns() {
        return new String[]{"column1","column2","column3","column4","column5"};
    }

    @Override
    public int linesToSkip() {
        return 1;
    }

    @Override
    public int linesToRead() {
        return 9999;
    }
}
