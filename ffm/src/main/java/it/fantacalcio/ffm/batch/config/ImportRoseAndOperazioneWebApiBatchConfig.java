package it.fantacalcio.ffm.batch.config;

import it.fantacalcio.ffm.batch.processor.ImportRoseAndOperazioneWebApiItemProcessor;
import it.fantacalcio.ffm.batch.writer.ImportOperazioneJpaItemWriter;
import it.fantacalcio.ffm.domain.entity.Operazione;
import it.fantacalcio.ffm.domain.model.fantaleghe.FantalegheTeam;
import it.fantacalcio.ffm.facade.ApiGatewayFacade;
import it.fantacalcio.ffm.utility.Constants;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

@Configuration
public class ImportRoseAndOperazioneWebApiBatchConfig {

    @Bean
    public Job importRoseAndOperazioneWebApiJob(JobRepository jobRepository,
                             Step importRoseAndOperazioneApiStep,
                             JobExecutionDecider importDecider,
                             JobExecutionListener jobExecutionListener) {
        return new JobBuilder("importRoseAndOperazioneWebApiJob", jobRepository)
                .listener(jobExecutionListener)
                .incrementer(new RunIdIncrementer())
                .start(importRoseAndOperazioneApiStep)
                .on("*").to(importDecider)
                .from(importDecider).on("COMPLETED").end()
                .from(importDecider).on("NOT_COMPLETED").fail()
                .build().build();
    }

    @Bean
    public Step importRoseAndOperazioneApiStep(JobRepository jobRepository,
                                    PlatformTransactionManager transactionManager,
                                    ItemReader<FantalegheTeam> readerFantalegheTeam,
                                    ItemProcessor<FantalegheTeam, List<Operazione>> processorFantalegheTeam,
                                    ItemWriter<List<Operazione>> listOperazioneItemWriter) {
        return new StepBuilder("importRoseAndOperazioneWebApiStep", jobRepository)
                .<FantalegheTeam, List<Operazione>>chunk(4, transactionManager)
                .reader(readerFantalegheTeam)
                .processor(processorFantalegheTeam)
                .writer(listOperazioneItemWriter)
                .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<FantalegheTeam, List<Operazione>> processorFantalegheTeam(ApiGatewayFacade apiGatewayFacade,
                                                                                   @Value("#{jobParameters['sessioneMercato']}") String sessioneMercato) {
        Constants.SessioneMercatoOpAcquistoEnum sessioneMercatoOpAcquistoEnum = Constants.SessioneMercatoOpAcquistoEnum.valueOf(sessioneMercato);
        return new ImportRoseAndOperazioneWebApiItemProcessor(apiGatewayFacade, sessioneMercatoOpAcquistoEnum);
    }

    @Bean
    public ItemWriter<List<Operazione>> listOperazioneItemWriter(JpaItemWriter<Operazione> operazioneJpaItemWriter) {
        return new ImportOperazioneJpaItemWriter(operazioneJpaItemWriter);
    }
}
