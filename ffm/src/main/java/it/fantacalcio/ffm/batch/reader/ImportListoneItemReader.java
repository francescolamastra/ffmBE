package it.fantacalcio.ffm.batch.reader;

import it.fantacalcio.ffm.batch.model.ListoneBatchRecord;
import it.fantacalcio.ffm.batch.utility.ExcelHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
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
                    Integer.parseInt(Objects.requireNonNull(ExcelHelper.getCellValue(row.getCell(0)))),
                    ExcelHelper.getCellValue(row.getCell(3)),
                    ExcelHelper.getCellValue(row.getCell(1)),
                    Integer.parseInt(Objects.requireNonNull(ExcelHelper.getCellValue(row.getCell(11))))
            );
        }
        return null;
    }
}