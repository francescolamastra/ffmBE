package it.fantacalcio.ffm.batch.config.drive.sheet;

public class ScambiSheetConfig implements SheetConfig{
    @Override
    public String[] getColumns() {
        return new String[]{"column1","column2","column3","column4","column5","column6","column7","column8","column9","column10","column11","column12","column13","column14","column15","column16","column17","column18","column19","column20","column21","column22","column23","column24","column25","column26","column27","column28"};
    }

    @Override
    public int linesToSkip() {
        return 2;
    }

    @Override
    public int linesToRead() {
        return 999;
    }
}
