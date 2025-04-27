package it.fantacalcio.ffm.batch.config;

import it.fantacalcio.ffm.batch.model.GiocatoreRosaOperazioneComposite;
import it.fantacalcio.ffm.batch.processor.ImportRoseWebApiItemProcessor;
import it.fantacalcio.ffm.batch.writer.ImportGiocatoreRosaJpaItemWriter;
import it.fantacalcio.ffm.domain.entity.GiocatoreRosa;
import it.fantacalcio.ffm.domain.entity.Operazione;
import it.fantacalcio.ffm.domain.model.fantaleghe.FantalegheTeam;
import it.fantacalcio.ffm.facade.ApiGatewayFacade;
import it.fantacalcio.ffm.utility.Constants;
import jakarta.persistence.EntityManagerFactory;
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

@Configuration
public class ImportRoseWebApiBatchConfig {

    @Bean
    public Job importRoseWebApiJob(JobRepository jobRepository,
                             Step importRoseWebApiStep,
                             JobExecutionDecider importDecider,
                             JobExecutionListener jobExecutionListener) {
        return new JobBuilder("importRoseWebApiJob", jobRepository)
                .listener(jobExecutionListener)
                .incrementer(new RunIdIncrementer())
                .start(importRoseWebApiStep)
                .on("*").to(importDecider)
                .from(importDecider).on("COMPLETED").end()
                .from(importDecider).on("NOT_COMPLETED").fail()
                .build().build();
    }

    @Bean
    public Step importRoseWebApiStep(JobRepository jobRepository,
                                    PlatformTransactionManager transactionManager,
                                    ItemReader<FantalegheTeam> readerFantalegheTeam,
                                    ItemProcessor<FantalegheTeam, GiocatoreRosaOperazioneComposite> processorFantalegheTeamToListGiocatoreRosa,
                                    ItemWriter<GiocatoreRosaOperazioneComposite> listGiocatoreRosaItemWriter) {
        return new StepBuilder("importRoseWebApiStep", jobRepository)
                .<FantalegheTeam, GiocatoreRosaOperazioneComposite>chunk(4, transactionManager)
                .reader(readerFantalegheTeam)
                .processor(processorFantalegheTeamToListGiocatoreRosa)
                .writer(listGiocatoreRosaItemWriter)
                .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<FantalegheTeam, GiocatoreRosaOperazioneComposite> processorFantalegheTeamToListGiocatoreRosa(ApiGatewayFacade apiGatewayFacade,
                                                                                                                      @Value("#{jobParameters['tipologiaRosa']}") String tipologiaRosa) {
        Constants.TipologiaRosaEnum tipologiaRosaEnum = Constants.TipologiaRosaEnum.valueOf(tipologiaRosa);
        return new ImportRoseWebApiItemProcessor(apiGatewayFacade, tipologiaRosaEnum);
    }

    @Bean
    public JpaItemWriter<GiocatoreRosa> giocatoreRosaItemWriter(EntityManagerFactory entityManagerFactory) {
        JpaItemWriter<GiocatoreRosa> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }

    @Bean
    public ItemWriter<GiocatoreRosaOperazioneComposite> listGiocatoreRosaItemWriter(JpaItemWriter<GiocatoreRosa> giocatoreRosaItemWriter, JpaItemWriter<Operazione> operazioneItemWriter){
        return new ImportGiocatoreRosaJpaItemWriter(giocatoreRosaItemWriter, operazioneItemWriter);
    }
}
