package it.fantacalcio.ffm.batch.reader;

import it.fantacalcio.ffm.batch.model.ListoneBatchRecord;
import org.apache.poi.ss.usermodel.*;
import org.springframework.batch.item.ItemReader;
import org.springframework.core.io.Resource;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Objects;

public class ImportListoneItemReader implements ItemReader<ListoneBatchRecord> {

    private final Iterator<Row> rowIterator;

    public ImportListoneItemReader(Resource resource, Long skipRows, String sheetName) throws Exception {
        try (InputStream inputStream = resource.getInputStream()) {
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IllegalArgumentException("Sheet " + sheetName + " does not exist");
            }
            this.rowIterator = sheet.iterator();
            // Salta le righe specificate
            for (int i = 0; i < skipRows && rowIterator.hasNext(); i++) {
                rowIterator.next();
            }
        }
    }

    @Override
    public ListoneBatchRecord read() {
        if (rowIterator.hasNext()) {
            Row row = rowIterator.next();

            return new ListoneBatchRecord(
                    Integer.parseInt(Objects.requireNonNull(getCellValue(row.getCell(0)))),
                    getCellValue(row.getCell(3)),
                    getCellValue(row.getCell(1)),
                    Integer.parseInt(Objects.requireNonNull(getCellValue(row.getCell(11))))
            );
        }
        return null;
    }

    private String getCellValue(Cell cell){
        if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue();
        } else if (cell.getCellType() == CellType.NUMERIC) {
            return String.valueOf((int) cell.getNumericCellValue());
        } else {
            return null;
        }
    }
}