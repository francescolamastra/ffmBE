package it.fantacalcio.ffm.batch.decider;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

public class ImportDecider implements JobExecutionDecider {

    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
        if(jobExecution.getStepExecutions().stream().anyMatch(stepEx -> stepEx.getStatus().isUnsuccessful())){
            return new FlowExecutionStatus("NOT_COMPLETED");
        }
        return new FlowExecutionStatus("COMPLETED");
    }
}
