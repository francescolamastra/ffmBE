package it.fantacalcio.ffm.batch.config;

import it.fantacalcio.ffm.batch.decider.ImportDecider;
import it.fantacalcio.ffm.batch.tasklet.DeleteFileTasklet;
import it.fantacalcio.ffm.domain.entity.Operazione;
import it.fantacalcio.ffm.domain.model.fantaleghe.FantalegheTeam;
import it.fantacalcio.ffm.facade.ApiGatewayFacade;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

@Configuration
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

    @Bean
    public JpaItemWriter<Operazione> operazioneItemWriter(EntityManagerFactory entityManagerFactory) {
        JpaItemWriter<Operazione> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }

    @Bean
    @StepScope
    public ItemReader<FantalegheTeam> readerFantalegheTeam(@Value("#{jobParameters['siglaNazione']}") String siglaNazione,
                                                           @Value("#{jobParameters['nickname']}") String nickname,
                                                           @Autowired ApiGatewayFacade apiGatewayFacade) {
        List<FantalegheTeam> dataList = apiGatewayFacade.getTeamsByNazione(siglaNazione, nickname);
        return new ListItemReader<>(dataList);
    }

    @Bean
    public JobExecutionDecider importListoneDecider() {
        return new ImportDecider();
    }
}