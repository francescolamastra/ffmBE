package it.fantacalcio.ffm.batch.utility;

import it.fantacalcio.ffm.batch.model.RisultatoCompetizioneBatchRecord;
import it.fantacalcio.ffm.utility.Constants;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExcelHelper {

    public static Map<Integer, List<RisultatoCompetizioneBatchRecord>> readExcel(String filePath, String sheetName, Integer skipRecords, Integer giornata) throws IOException {
        Map<Integer, List<RisultatoCompetizioneBatchRecord>> allTables = new HashMap<>();
        boolean readAll = giornata.equals(Constants.DEFAULT_ALL);
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            int rowIndex = skipRecords;
            int currentGiornata = 1;
            boolean found = false;

            while (rowIndex < sheet.getPhysicalNumberOfRows() && !found) {
                for (int colIndex = 0; colIndex < sheet.getRow(rowIndex).getPhysicalNumberOfCells(); colIndex += 6) {
                    Row headerRow = sheet.getRow(rowIndex);
                    Integer giornataCompetizione = Integer.valueOf(extractNumber(getCellValue(headerRow.getCell(colIndex))));
                    Integer giornataSerieA = Integer.valueOf(extractNumber(getCellValue(headerRow.getCell(colIndex + 2))));

                    if (readAll || giornata.equals(currentGiornata)) {
                        List<RisultatoCompetizioneBatchRecord> currentTable = new ArrayList<>();
                        for (int i = 1; i <= 10; i++) { // Salta l'intestazione e leggi le 10 righe successive
                            Row row = sheet.getRow(rowIndex + i);
                            if (row != null) {
                                RisultatoCompetizioneBatchRecord record = new RisultatoCompetizioneBatchRecord();
                                record.setSquadraA(getCellValue(row.getCell(colIndex)));
                                record.setSquadraB(getCellValue(row.getCell(colIndex + 3)));
                                record.setRisultato(getCellValue(row.getCell(colIndex + 4)));
                                record.setGiornataCompetizione(giornataCompetizione);
                                record.setGiornataSerieA(giornataSerieA);
                                currentTable.add(record);
                            }
                        }
                        if(currentTable.stream().noneMatch(risultatoCompetizioneBatchRecord -> risultatoCompetizioneBatchRecord.getRisultato().equals(Constants.SEPARATORE_RISULTATO)))
                            allTables.put(currentGiornata, currentTable);
                        if (!readAll) {
                            found = true;
                            break;
                        }
                    }
                    currentGiornata++;
                }
                rowIndex += 11; // Passa alla prossima coppia di tabelle
            }
        }

        return allTables;
    }

    public static String getCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    double numericValue = cell.getNumericCellValue();
                    if (numericValue == (long) numericValue) {
                        return String.format("%d", (long) numericValue);
                    } else {
                        return String.valueOf(numericValue);
                    }
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }

    private static String extractNumber(String text) {
        if (text == null) {
            return null;
        }
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(text);
        return matcher.find() ? matcher.group() : null;
    }
}
