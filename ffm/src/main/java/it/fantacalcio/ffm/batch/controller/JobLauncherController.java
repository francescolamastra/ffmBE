package it.fantacalcio.ffm.batch.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.fantacalcio.ffm.batch.service.JobService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@RestController
@RequestMapping(value = "/ffm/batch")
@Tag(name = "JobLauncherController", description = "Gestione dei batch di FFM")
public class JobLauncherController{

    private final JobService jobService;
    private final Environment env;

    public JobLauncherController(JobService jobService, Environment env) {
        this.jobService = jobService;
        this.env = env;
    }

    @PostMapping(value = "/importGiocatori",  consumes = "multipart/form-data")
    @Operation(summary = "Import dei giocatori tramite excel fantagazzetta")
    public String importExcel(@RequestParam("file") MultipartFile file,
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
            String fileName = Objects.requireNonNull(file.getOriginalFilename());
            Path filePath = Paths.get("uploads", fileName);
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, file.getBytes());

            // Esegui il job in modo asyncrono
            jobService.runImportGiocatoriJob(filePath.toString(), skipRows.longValue(), sheetName);
            return "Job started";
        } catch (Exception e) {
            return "Job failed:"+e;
        }
    }
}
