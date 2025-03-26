package it.fantacalcio.ffm.batch.reader;

import it.fantacalcio.ffm.batch.model.RisultatoCompetizioneBatchRecord;
import it.fantacalcio.ffm.batch.utility.ExcelHelper;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ImportRisultatoCompetizioneItemReader implements ItemReader<RisultatoCompetizioneBatchRecord> {

    //private static final Logger logger = LoggerFactory.getLogger(ImportRisultatoCompetizioneItemReader.class);
    private final Iterator<RisultatoCompetizioneBatchRecord> matchRecordIterator;

    public ImportRisultatoCompetizioneItemReader(Resource resource, Integer skipRows, String sheetName, Integer giornata) throws Exception {
        this.matchRecordIterator = readExcelFile(resource, skipRows, sheetName, giornata).iterator();
    }
    private List<RisultatoCompetizioneBatchRecord> readExcelFile(Resource resource, Integer skipRows, String sheetName, Integer giornata) throws Exception {
        try (InputStream inputStream = resource.getInputStream()) {
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IllegalArgumentException("Sheet " + sheetName + " does not exist");
            }
            Map<Integer, List<RisultatoCompetizioneBatchRecord>> allTables = ExcelHelper.readExcel(resource.getFile().getAbsolutePath(), sheetName, skipRows, giornata);
            return allTables.values().stream().flatMap(List::stream).toList();
        } catch (IOException e) {
            //    logger.error("Errore durante la lettura del file Excel", e);
            throw new Exception("Errore durante la lettura del file Excel", e);
        } catch (IllegalArgumentException e) {
            //    logger.error("Errore: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public RisultatoCompetizioneBatchRecord read() {
        if (matchRecordIterator.hasNext()) {
            return matchRecordIterator.next();
        }
        return null;
    }
}
