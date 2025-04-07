package it.fantacalcio.ffm.batch.config;

import it.fantacalcio.ffm.domain.entity.Squadra;
import it.fantacalcio.ffm.domain.model.fantaleghe.FantalegheTeam;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class ImportSquadreWebApiBatchConfig {

    @Bean
    public Job importSquadreWebApiJob(JobRepository jobRepository,
                             Step importSquadreWebApiStep,
                             JobExecutionDecider importDecider,
                             JobExecutionListener jobExecutionListener) {
        return new JobBuilder("importSquadreWebApiJob", jobRepository)
                .listener(jobExecutionListener)
                .incrementer(new RunIdIncrementer())
                .start(importSquadreWebApiStep)
                .on("*").to(importDecider)
                .from(importDecider).on("COMPLETED").end()
                .from(importDecider).on("NOT_COMPLETED").fail()
                .build().build();
    }

    @Bean
    public Step importSquadreWebApiStep(JobRepository jobRepository,
                                    PlatformTransactionManager transactionManager,
                                    ItemReader<FantalegheTeam> readerFantalegheTeam,
                                    ItemProcessor<FantalegheTeam, Squadra> processorFantalegheTeam,
                                    ItemWriter<Squadra> squadraJpaItemWriter) {
        return new StepBuilder("importSquadreWebApiStep", jobRepository)
                .<FantalegheTeam, Squadra>chunk(13, transactionManager)
                .reader(readerFantalegheTeam)
                .processor(processorFantalegheTeam)
                .writer(squadraJpaItemWriter)
                .build();
    }

    @Bean
    public JpaItemWriter<Squadra> squadraJpaItemWriter(EntityManagerFactory entityManagerFactory) {
        JpaItemWriter<Squadra> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }
}
