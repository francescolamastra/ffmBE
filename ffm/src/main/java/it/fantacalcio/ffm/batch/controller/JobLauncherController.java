package it.fantacalcio.ffm.batch.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.fantacalcio.ffm.batch.service.JobService;
import it.fantacalcio.ffm.batch.utility.FileManager;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.FileAlreadyExistsException;
import java.util.Objects;

import static it.fantacalcio.ffm.utility.Constants.UPLOADS_DIR;

@RestController
@RequestMapping(value = "/ffm/batch")
@Tag(name = "JobLauncherController", description = "Gestione dei batch di FFM")
public class JobLauncherController{

    private final JobService jobService;
    private final Environment env;
    private final FileManager fileManager;

    public JobLauncherController(JobService jobService, Environment env, FileManager fileManager) {
        this.jobService = jobService;
        this.env = env;
        this.fileManager = fileManager;
    }

    @PostMapping(value = "/importListone",  consumes = "multipart/form-data")
    @Operation(summary = "Import del listone tramite excel fantagazzetta")
    public String importListone(@RequestParam("file") MultipartFile file,
                              @RequestParam(value = "skipRows", required = false) Integer skipRows,
                              @RequestParam(value = "sheetName", required = false) String sheetName) {
        try {
            // Recupera i valori predefiniti da application.yml se i parametri non sono forniti o sono vuoti
            if (skipRows == null || skipRows.toString().isEmpty()) {
                skipRows = Integer.parseInt(Objects.requireNonNull(env.getProperty("ffm.batch.excel-giocatori.skip-rows")));
            }
            if (sheetName == null || sheetName.isEmpty()) {
                sheetName = env.getProperty("ffm.batch.excel-giocatori.sheet-name");
            }

            // Salva il file in una directory specifica interna all'applicazione
            String filePath = fileManager.copyToInDirectory(file, UPLOADS_DIR);

            // Esegui il job in modo asyncrono
            jobService.runImportListoneJob(filePath, skipRows.longValue(), sheetName);
            return "Job importListone started";
        } catch (FileAlreadyExistsException e) {
            return "Job importListone failed: " + e.getMessage();
        } catch (Exception e) {
            return "Job importListone failed:"+e;
        }
    }

    @PostMapping(value = "/importRose",  consumes = "multipart/form-data")
    @Operation(summary = "Import delle rose tramite csv fantagazzetta")
    public String importRose(@RequestParam("file") MultipartFile file,
                              @RequestParam(value = "skipRows", required = false) Integer skipRows) {
        try {
            // Recupera i valori predefiniti da application.yml se i parametri non sono forniti o sono vuoti
            if (skipRows == null || skipRows.toString().isEmpty()) {
                skipRows = Integer.parseInt(Objects.requireNonNull(env.getProperty("ffm.batch.csv-rose.skip-rows")));
            }

            // Salva il file in una directory specifica interna all'applicazione
            String filePath = fileManager.copyToInDirectory(file, UPLOADS_DIR);

            // Esegui il job in modo asyncrono
            jobService.runImportRoseJob(filePath, skipRows.longValue());
            return "Job importRose started!";
        } catch (FileAlreadyExistsException e) {
            return "Job importRose failed: " + e.getMessage();
        } catch (Exception e) {
            return "Job importRose failed:"+e;
        }
    }

    @PostMapping(value = "/importRisultatiCompetizione",  consumes = "multipart/form-data")
    @Operation(summary = "Import dei risultati di una competizione tramite excel fantagazzetta")
    public String importRisultatiCompetizione(@RequestParam("file") MultipartFile file,
                                              @RequestParam(value = "skipRows", required = false) Long skipRows,
                                              @RequestParam(value = "sheetName", required = false) String sheetName,
                                              @RequestParam(value = "giornata", required = false) Long giornata,
                                              @RequestParam(value = "competizione") String competizione,
                                              @RequestParam(value = "faseCompetizione") String faseCompetizione) {
        // Salva il file in una directory specifica interna all'applicazione
        String filePath = null;
        try {
            filePath = fileManager.copyToInDirectory(file, UPLOADS_DIR);
            // Recupera i valori predefiniti da application.yml se i parametri non sono forniti o sono vuoti
            if (skipRows == null) {
                skipRows = Long.parseLong(Objects.requireNonNull(env.getProperty("ffm.batch.excel-risultati-competizione.skip-rows")));
            }
            if (sheetName == null || sheetName.isEmpty()) {
                sheetName = env.getProperty("ffm.batch.excel-risultati-competizione.sheet-name");
            }
            if (giornata == null) {
                giornata = Long.parseLong(Objects.requireNonNull(env.getProperty("ffm.batch.excel-risultati-competizione.giornata")));
            }

            // Esegui il job in modo asyncrono
            jobService.runImportRisultatiCompetizione(filePath, skipRows, sheetName, competizione, faseCompetizione, giornata);
            return "Job importRisultatiCompetizione started";
        } catch (Exception e) {
            fileManager.deleteFile(filePath);
            return "Job importRisultatiCompetizione failed:"+e;
        }
    }
}
