package it.fantacalcio.ffm.batch.listener;

import it.fantacalcio.ffm.batch.utility.StepStatusUpdater;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class CustomJobExecutionListener implements JobExecutionListener {
    private final StepStatusUpdater stepStatusUpdater;

    public CustomJobExecutionListener(StepStatusUpdater stepStatusUpdater){
        this.stepStatusUpdater = stepStatusUpdater;
    }

    @Override
    public void afterJob(JobExecution jobExecution){
        stepStatusUpdater.updateStepStatus(jobExecution);
    }
}
