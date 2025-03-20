package it.fantacalcio.ffm.batch.utility;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.stereotype.Component;

@Component
public class StepStatusUpdater {

    private final JobRepository jobRepository;

    public StepStatusUpdater(JobRepository jobRepository){
        this.jobRepository = jobRepository;
    }

    public void updateStepStatus(JobExecution jobExecution) {
        for (StepExecution stepExecution : jobExecution.getStepExecutions()) {
            if (stepExecution.getStatus().equals(BatchStatus.ABANDONED)) {
                stepExecution.setStatus(BatchStatus.FAILED);
                jobRepository.update(stepExecution);
            }
        }
    }
}
