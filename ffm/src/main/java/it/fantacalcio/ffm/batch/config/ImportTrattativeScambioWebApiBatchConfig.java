package it.fantacalcio.ffm.batch.config;

import it.fantacalcio.ffm.batch.model.TrattativaScambioBatch;
import it.fantacalcio.ffm.batch.processor.ImportTrattativeScambioWebApiItemProcessor;
import it.fantacalcio.ffm.batch.writer.ImportTrattativeScambioWebApiJpaItemWriter;
import it.fantacalcio.ffm.domain.model.fantaleghe.FantalegheTrattativeScambio;
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
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class ImportTrattativeScambioWebApiBatchConfig {

    @Bean
    public Job importTrattativeScambioWebApiJob(JobRepository jobRepository,
                             Step importTrattativeScambioWebApiStep,
                             JobExecutionDecider importDecider,
                             JobExecutionListener jobExecutionListener) {
        return new JobBuilder("importTrattativeScambioWebApiJob", jobRepository)
                .listener(jobExecutionListener)
                .incrementer(new RunIdIncrementer())
                .start(importTrattativeScambioWebApiStep)
                .on("*").to(importDecider)
                .from(importDecider).on("COMPLETED").end()
                .from(importDecider).on("NOT_COMPLETED").fail()
                .build().build();
    }

    @Bean
    public Step importTrattativeScambioWebApiStep(JobRepository jobRepository,
                                    PlatformTransactionManager transactionManager,
                                    ItemReader<FantalegheTrattativeScambio.Scambio> readerFantalegheTrattativeScambio,
                                    ItemProcessor<FantalegheTrattativeScambio.Scambio,TrattativaScambioBatch> processorFantalegheTrattativeScambio,
                                    ItemWriter<TrattativaScambioBatch> trattativaScambioItemWriter) {
        return new StepBuilder("importTrattativeScambioWebApiStep", jobRepository)
                .<FantalegheTrattativeScambio.Scambio, TrattativaScambioBatch>chunk(4, transactionManager)
                .reader(readerFantalegheTrattativeScambio)
                .processor(processorFantalegheTrattativeScambio)
                .writer(trattativaScambioItemWriter)
                .build();
    }

    @Bean
    @StepScope
    public ItemReader<FantalegheTrattativeScambio.Scambio> readerFantalegheTrattativeScambio(@Value("#{jobParameters['siglaNazione']}") String siglaNazione,
                                                                                                      @Value("#{jobParameters['nickname']}") String nickname,
                                                                                                      @Value("#{jobParameters['siglaCategoria']}") String siglaCategoria,
                                                                                                      @Value("#{jobParameters['idMercato']}") String idMercato,
                                                                                                      @Value("#{jobParameters['tipoMercato']}") String tipoMercato,
                                                                                                      @Autowired ApiGatewayFacade apiGatewayFacade) {
        FantalegheTrattativeScambio trattativeScambio = apiGatewayFacade.getTrattativeScambioByNazioneAndCategoria(siglaNazione, siglaCategoria, idMercato, tipoMercato, nickname);
        return new ListItemReader<>(trattativeScambio.getListaScambi());
    }

    @Bean
    @StepScope
    public ItemProcessor<FantalegheTrattativeScambio.Scambio, TrattativaScambioBatch> processorFantalegheTrattativeScambio(ApiGatewayFacade apiGatewayFacade,
                                                                                                                           @Value("#{jobParameters['sessioneMercato']}") String sessioneMercato){
        Constants.SessioneMercatoTrattiveScambioEnum sessioneMercatoTrattiveScambioEnum = Constants.SessioneMercatoTrattiveScambioEnum.valueOf(sessioneMercato);
        return new ImportTrattativeScambioWebApiItemProcessor(apiGatewayFacade, sessioneMercatoTrattiveScambioEnum);
    }

    @Bean
    public ItemWriter<TrattativaScambioBatch> customTrattativaScambioBatchJpaItemWriter(@Autowired ApiGatewayFacade apiGatewayFacade) {
        return new ImportTrattativeScambioWebApiJpaItemWriter(apiGatewayFacade);
    }
}
