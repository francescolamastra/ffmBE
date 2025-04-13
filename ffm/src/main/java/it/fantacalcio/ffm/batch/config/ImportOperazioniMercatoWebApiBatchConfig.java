package it.fantacalcio.ffm.batch.config;

import it.fantacalcio.ffm.batch.processor.ImportOperazioniMercatoWebApiItemProcessor;
import it.fantacalcio.ffm.domain.entity.Operazione;
import it.fantacalcio.ffm.domain.model.fantaleghe.FantalegheOperazioneMercato;
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
public class ImportOperazioniMercatoWebApiBatchConfig {

    @Bean
    public Job importOperazioniMercatoWebApiJob(JobRepository jobRepository,
                             Step importOperazioniMercatoWebApiStep,
                             JobExecutionDecider importDecider,
                             JobExecutionListener jobExecutionListener) {
        return new JobBuilder("importOperazioniMercatoWebApiJob", jobRepository)
                .listener(jobExecutionListener)
                .incrementer(new RunIdIncrementer())
                .start(importOperazioniMercatoWebApiStep)
                .on("*").to(importDecider)
                .from(importDecider).on("COMPLETED").end()
                .from(importDecider).on("NOT_COMPLETED").fail()
                .build().build();
    }

    @Bean
    public Step importOperazioniMercatoWebApiStep(JobRepository jobRepository,
                                    PlatformTransactionManager transactionManager,
                                    ItemReader<FantalegheOperazioneMercato.OperazioneMercato> readerFantalegheOperazioneMercato,
                                    ItemProcessor<FantalegheOperazioneMercato.OperazioneMercato,Operazione> processorFantalegheOperazioneMercato,
                                    ItemWriter<Operazione> operazioneItemWriter) {
        return new StepBuilder("importOperazioniMercatoWebApiStep", jobRepository)
                .<FantalegheOperazioneMercato.OperazioneMercato, Operazione>chunk(10, transactionManager)
                .reader(readerFantalegheOperazioneMercato)
                .processor(processorFantalegheOperazioneMercato)
                .writer(operazioneItemWriter)
                .build();
    }

    @Bean
    @StepScope
    public ItemReader<FantalegheOperazioneMercato.OperazioneMercato> readerFantalegheOperazioneMercato(@Value("#{jobParameters['siglaNazione']}") String siglaNazione,
                                                                                                      @Value("#{jobParameters['nickname']}") String nickname,
                                                                                                      @Value("#{jobParameters['siglaCategoria']}") String siglaCategoria,
                                                                                                      @Value("#{jobParameters['idMercato']}") String idMercato,
                                                                                                      @Value("#{jobParameters['tipoMercato']}") String tipoMercato,
                                                                                                      @Autowired ApiGatewayFacade apiGatewayFacade) {
        FantalegheOperazioneMercato operazioneMercato = apiGatewayFacade.getOperazioniMercatoByNazioneAndCategoria(siglaNazione, siglaCategoria, idMercato, tipoMercato, nickname);
        return new ListItemReader<>(operazioneMercato.getListaOperazioni());
    }

    @Bean
    @StepScope
    public ItemProcessor<FantalegheOperazioneMercato.OperazioneMercato, Operazione> processorFantalegheOperazioneMercato(ApiGatewayFacade apiGatewayFacade,
                                                                                                                         @Value("#{jobParameters['sessioneMercato']}") String sessioneMercato,
                                                                                                                         @Value("#{jobParameters['tipoMercato']}") String tipoMercato){
        boolean isMercatoAcquisti = Constants.TipologiaMercatoFantalegheEnum.valueOf(tipoMercato).isMercatoAcquisti();
        Constants.SessioneMercatoOpAcquistoEnum sessioneMercatoEnum = Constants.SessioneMercatoOpAcquistoEnum.valueOf(sessioneMercato);
        return new ImportOperazioniMercatoWebApiItemProcessor(apiGatewayFacade, isMercatoAcquisti, sessioneMercatoEnum);
    }
}
