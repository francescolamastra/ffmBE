package it.fantacalcio.ffm.batch.config;

import it.fantacalcio.ffm.batch.tasklet.DeleteFileTasklet;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class CommonBatchConfig {

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(10);
        taskExecutor.setMaxPoolSize(20);
        taskExecutor.setQueueCapacity(50);
        taskExecutor.setThreadNamePrefix("Batch-");
        taskExecutor.initialize();
        return taskExecutor;
    }
    @Bean
    public Step deleteInputFileStep(JobRepository jobRepository,
                               PlatformTransactionManager transactionManager,
                               DeleteFileTasklet deleteFileTasklet) {
        return new StepBuilder("deleteInputFileStep", jobRepository)
                .tasklet(deleteFileTasklet, transactionManager)
                .allowStartIfComplete(true)
                .build();
    }
}