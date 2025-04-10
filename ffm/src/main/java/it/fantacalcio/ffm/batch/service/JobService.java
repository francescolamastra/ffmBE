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
    private Job importRisultatiCompetizioneJob;

    @Autowired
    private Job importRoseWebApiJob;

    @Autowired
    private Job importSquadreWebApiJob;

    @Autowired
    private Job importOperazioniMercatoWebApiJob;

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
                } catch (JobExecutionAlreadyRunningException | JobParametersInvalidException | JobRestartException |
                         JobInstanceAlreadyCompleteException ex) {
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
                try {
                    throw e;
                } catch (JobExecutionAlreadyRunningException | JobParametersInvalidException | JobRestartException |
                         JobInstanceAlreadyCompleteException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public void runImportRoseWebApiJob(String siglaNazione, String nickname) {
        taskExecutor.execute(() -> {
            try {
                jobLauncher.run(importRoseWebApiJob, new JobParametersBuilder()
                        .addString("siglaNazione", siglaNazione)
                        .addString("nickname", nickname)
                        .toJobParameters());
            } catch (Exception e) {
                try {
                    throw e;
                } catch (JobExecutionAlreadyRunningException | JobParametersInvalidException | JobRestartException |
                         JobInstanceAlreadyCompleteException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public void runImportSquadreWebApiJob(String siglaNazione, String nickname) {
        taskExecutor.execute(() -> {
            try {
                jobLauncher.run(importSquadreWebApiJob, new JobParametersBuilder()
                        .addString("siglaNazione", siglaNazione)
                        .addString("nickname", nickname)
                        .toJobParameters());
            } catch (Exception e) {
                try {
                    throw e;
                } catch (JobExecutionAlreadyRunningException | JobParametersInvalidException | JobRestartException |
                         JobInstanceAlreadyCompleteException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public void runImportRisultatiCompetizione(String filePath, Long skipRows, String sheetName, String competizione, String faseCompetizione, Long giornata) {
        taskExecutor.execute(() -> {
            try {
                jobLauncher.run(importRisultatiCompetizioneJob, new JobParametersBuilder()
                        .addString("filePath", filePath)
                        .addLong("skipRows", skipRows)
                        .addString("sheetName", sheetName)
                        .addString("competizione", competizione)
                        .addString("faseCompetizione", faseCompetizione)
                        .addLong("giornata", giornata)
                        .toJobParameters());
            } catch (Exception e) {
                try {
                    throw e;
                } catch (JobExecutionAlreadyRunningException | JobParametersInvalidException | JobRestartException |
                         JobInstanceAlreadyCompleteException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public void runImportOperazioniMercatoWebApiJob(String siglaNazione, String siglaCategoria, String idMercato, String tipoMercato, String nickname) {
        taskExecutor.execute(() -> {
            try {
                jobLauncher.run(importOperazioniMercatoWebApiJob, new JobParametersBuilder()
                        .addString("siglaNazione", siglaNazione)
                        .addString("siglaCategoria", siglaCategoria)
                        .addString("idMercato", idMercato)
                        .addString("tipoMercato", tipoMercato)
                        .addString("nickname", nickname)
                        .toJobParameters());
            } catch (Exception e) {
                try {
                    throw e;
                } catch (JobExecutionAlreadyRunningException | JobParametersInvalidException | JobRestartException |
                         JobInstanceAlreadyCompleteException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
}
