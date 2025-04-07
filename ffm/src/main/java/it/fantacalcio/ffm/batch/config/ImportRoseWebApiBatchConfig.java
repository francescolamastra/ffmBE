package it.fantacalcio.ffm.batch.config;

import it.fantacalcio.ffm.domain.entity.Operazione;
import it.fantacalcio.ffm.domain.model.fantaleghe.FantalegheTeam;
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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

@Configuration
public class ImportRoseWebApiBatchConfig {

    @Bean
    public Job importRoseWebApiJob(JobRepository jobRepository,
                             Step importRoseApiStep,
                             JobExecutionDecider importDecider,
                             JobExecutionListener jobExecutionListener) {
        return new JobBuilder("importRoseWebApiJob", jobRepository)
                .listener(jobExecutionListener)
                .incrementer(new RunIdIncrementer())
                .start(importRoseApiStep)
                .on("*").to(importDecider)
                .from(importDecider).on("COMPLETED").end()
                .from(importDecider).on("NOT_COMPLETED").fail()
                .build().build();
    }

    @Bean
    public Step importRoseApiStep(JobRepository jobRepository,
                                    PlatformTransactionManager transactionManager,
                                    ItemReader<FantalegheTeam> readerFantalegheTeam,
                                    ItemProcessor<FantalegheTeam, List<Operazione>> processorFantalegheTeam,
                                    ItemWriter<List<Operazione>> listOperazioneItemWriter) {
        return new StepBuilder("importRoseWebApiStep", jobRepository)
                .<FantalegheTeam, List<Operazione>>chunk(4, transactionManager)
                .reader(readerFantalegheTeam)
                .processor(processorFantalegheTeam)
                .writer(listOperazioneItemWriter)
                .build();
    }
}
