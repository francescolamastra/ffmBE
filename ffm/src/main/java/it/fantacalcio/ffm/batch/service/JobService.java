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
    private Job importRoseAndOperazioneJob;

    @Autowired
    private Job importRisultatiCompetizioneJob;

    @Autowired
    private Job importRoseAndOperazioneWebApiJob;

    @Autowired
    private Job importSquadreWebApiJob;

    @Autowired
    private Job importOperazioniMercatoWebApiJob;

    @Autowired
    private Job importTrattativeScambioWebApiJob;

    @Autowired
    private Job importRoseWebApiJob;

    @Autowired
    private Job importDriveCsvJob;

    @Autowired
    private TaskExecutor taskExecutor;

    public void runImportListoneJob(String filePath, Long skipRows, String sheetName, String tipologiaListone, Long annoInizioStagione) {
        taskExecutor.execute(() -> {
            try {
                jobLauncher.run(importListoneJob, new JobParametersBuilder()
                        .addString("filePath", filePath)
                        .addLong("skipRows", skipRows)
                        .addString("sheetName", sheetName)
                        .addString("tipologiaListone", tipologiaListone)
                        .addLong("annoInizioStagione", annoInizioStagione)
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

    public void runImportRoseAndOperazioneJob(String filePath, Long skipRows, String sessioneMercato) {
        taskExecutor.execute(() -> {
            try {
                jobLauncher.run(importRoseAndOperazioneJob, new JobParametersBuilder()
                        .addString("filePath", filePath)
                        .addLong("skipRows", skipRows)
                        .addString("sessioneMercato", sessioneMercato)
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

    public void runImportRoseAndOperazioneWebApiJob(String siglaNazione, String sessioneMercato, String nickname) {
        taskExecutor.execute(() -> {
            try {
                jobLauncher.run(importRoseAndOperazioneWebApiJob, new JobParametersBuilder()
                        .addString("siglaNazione", siglaNazione)
                        .addString("sessioneMercato", sessioneMercato)
                        .addString("nickname", nickname, false)
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

    public void runImportRoseWebApiJob(String siglaNazione, String tipologiaRosa, String nickname) {
        taskExecutor.execute(() -> {
            try {
                jobLauncher.run(importRoseWebApiJob, new JobParametersBuilder()
                        .addString("siglaNazione", siglaNazione)
                        .addString("tipologiaRosa", tipologiaRosa)
                        .addString("nickname", nickname, false)
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
                        .addString("nickname", nickname, false)
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

    public void runImportOperazioniMercatoWebApiJob(String siglaNazione, String siglaCategoria, String idMercato, String tipoMercato, String sessioneMercato, String nickname) {
        taskExecutor.execute(() -> {
            try {
                jobLauncher.run(importOperazioniMercatoWebApiJob, new JobParametersBuilder()
                        .addString("siglaNazione", siglaNazione)
                        .addString("siglaCategoria", siglaCategoria)
                        .addString("idMercato", idMercato)
                        .addString("tipoMercato", tipoMercato)
                        .addString("sessioneMercato", sessioneMercato)
                        .addString("nickname", nickname, false)
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

    public void runImportTrattativeScambioWebApiJob(String siglaNazione, String siglaCategoria, String idMercato, String tipoMercato, String sessioneMercato, String nickname) {
        taskExecutor.execute(() -> {
            try {
                jobLauncher.run(importTrattativeScambioWebApiJob, new JobParametersBuilder()
                        .addString("siglaNazione", siglaNazione)
                        .addString("siglaCategoria", siglaCategoria)
                        .addString("idMercato", idMercato)
                        .addString("tipoMercato", tipoMercato)
                        .addString("sessioneMercato", sessioneMercato)
                        .addString("nickname", nickname, false)
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

    public void runImportDriveCsvJob(String idFile, String tipologiaSheet, String tipologiaRosa, String idSheet, Long idSquadra, long linesToSkip, long linesToRead) {
        taskExecutor.execute(() -> {
            try {
                jobLauncher.run(importDriveCsvJob, new JobParametersBuilder()
                                .addString("idFile", idFile)
                                .addString("tipologiaSheet", tipologiaSheet)
                                .addString("tipologiaRosa", tipologiaRosa)
                                .addString("idSheet", idSheet)
                                .addLong("idSquadra", idSquadra)
                                .addLong("linesToSkip", linesToSkip)
                                .addLong("linesToRead", linesToRead)
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
