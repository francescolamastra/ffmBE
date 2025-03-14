package it.fantacalcio.ffm.batch.service;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

@Service
public class JobService {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job importGiocatoriJob;

    @Autowired
    private TaskExecutor taskExecutor;

    public void runImportGiocatoriJob(String filePath, Long skipRows, String sheetName) {
        taskExecutor.execute(() -> {
            try {
                jobLauncher.run(importGiocatoriJob, new JobParametersBuilder()
                        .addString("filePath", filePath)
                        .addLong("skipRows", skipRows)
                        .addString("sheetName", sheetName)
                        .toJobParameters());
            } catch (Exception e) {
                // Gestisci l'eccezione
            }
        });
    }
}
