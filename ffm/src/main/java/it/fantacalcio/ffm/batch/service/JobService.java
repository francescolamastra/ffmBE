package it.fantacalcio.ffm.batch.service;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

@Service
public class JobService {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job importListoneJob;

    @Autowired
    private Job importRoseJob;

    @Autowired
    private TaskExecutor taskExecutor;

    public void runImportListoneJob(String filePath, Long skipRows, String sheetName) {
        taskExecutor.execute(() -> {
            try {
                jobLauncher.run(importListoneJob, new JobParametersBuilder()
                        .addString("filePath", filePath)
                        .addLong("skipRows", skipRows)
                        .addString("sheetName", sheetName)
                        .toJobParameters());
            } catch (Exception e) {
                try {
                    throw e;
                } catch (JobExecutionAlreadyRunningException | JobParametersInvalidException ex) {
                    throw new RuntimeException(ex);
                } catch (JobRestartException ex) {
                    throw new RuntimeException(ex);
                } catch (JobInstanceAlreadyCompleteException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public void runImportRoseJob(String filePath, Long skipRows) {
        taskExecutor.execute(() -> {
            try {
                jobLauncher.run(importRoseJob, new JobParametersBuilder()
                        .addString("filePath", filePath)
                        .addLong("skipRows", skipRows)
                        .toJobParameters());
            } catch (Exception e) {
                // Gestisci l'eccezione
            }
        });
    }
}
