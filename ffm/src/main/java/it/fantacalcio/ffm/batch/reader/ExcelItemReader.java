package it.fantacalcio.ffm.batch.reader;

import it.fantacalcio.ffm.domain.dto.GiocatoreDto;
import org.apache.poi.ss.usermodel.*;
import org.springframework.batch.item.ItemReader;
import org.springframework.core.io.Resource;

import java.io.InputStream;
import java.util.Iterator;

public class ExcelItemReader implements ItemReader<GiocatoreDto> {

    private final Iterator<Row> rowIterator;

    public ExcelItemReader(Resource resource, int skipRows, String sheetName) throws Exception {
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
    public GiocatoreDto read() {
        if (rowIterator.hasNext()) {
            Row row = rowIterator.next();

            return new GiocatoreDto(0,
                    Integer.parseInt(getCellValue(row.getCell(0))),
                    getCellValue(row.getCell(3)),
                    getCellValue(row.getCell(1)));
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