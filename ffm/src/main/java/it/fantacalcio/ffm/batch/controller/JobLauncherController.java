package it.fantacalcio.ffm.batch.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@RestController
@RequestMapping(value = "/ffm/batch")
@Tag(name = "JobLauncherController", description = "Gestione dei batch di FFM")
public class JobLauncherController {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job importGiocatoriJob;

    @PostMapping(value = "/importGiocatori",  consumes = "multipart/form-data")
    @Operation(summary = "Import dei giocatori tramite excel fantagazzetta")
    public String importExcel(@RequestParam("file") MultipartFile file) {
        try {
            // Salva il file in una directory specifica
            String fileName = Objects.requireNonNull(file.getOriginalFilename());
            Path filePath = Paths.get("uploads", fileName);
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, file.getBytes());

            // Esegui il job con il percorso del file
            jobLauncher.run(importGiocatoriJob, new JobParametersBuilder()
                    .addString("filePath", filePath.toString())
                    .toJobParameters());
            return "Job started";
        } catch (Exception e) {
            return "Job failed";
        }
    }
}
