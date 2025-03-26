package it.fantacalcio.ffm.batch.config;

import it.fantacalcio.ffm.batch.model.MatchRecord;
import it.fantacalcio.ffm.batch.model.RisultatoCompetizioneBatchRecord;
import it.fantacalcio.ffm.batch.processor.RisultatoCompetizioneBatchRecordItemProcessor;
import it.fantacalcio.ffm.batch.reader.ImportRisultatoCompetizioneItemReader;
import it.fantacalcio.ffm.domain.dto.*;
import it.fantacalcio.ffm.domain.entity.RisultatoCompetizione;
import it.fantacalcio.ffm.facade.ApiGatewayFacade;
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
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Arrays;
import java.util.List;

@Configuration
public class ImportRisultatiCompetizioneBatchConfig {

    @Bean
    public Job importRisultatiCompetizioneJob(JobRepository jobRepository,
                             Step importRisultatiCompetizioneStep,
                             Step deleteInputFileStep,
                             JobExecutionDecider importDecider,
                             JobExecutionListener jobExecutionListener) {
        return new JobBuilder("importRisultatiCompetizioneJob", jobRepository)
                .listener(jobExecutionListener)
                .incrementer(new RunIdIncrementer())
                .start(importRisultatiCompetizioneStep)
                .on("*").to(deleteInputFileStep)
                .from(deleteInputFileStep).on("*").to(importDecider)
                .from(importDecider).on("COMPLETED").end()
                .from(importDecider).on("NOT_COMPLETED").fail()
                .build().build();
    }

    @Bean
    public Step importRisultatiCompetizioneStep(JobRepository jobRepository,
                                    PlatformTransactionManager transactionManager,
                                    ItemReader<RisultatoCompetizioneBatchRecord> risultatoCompetizioneBatchRecordItemReader,
                                    ItemProcessor<RisultatoCompetizioneBatchRecord, MatchRecord> compositeRisultatoCompetizioneItemProcessor,
                                    ItemWriter<MatchRecord> matchRecordItemWriter) {
        return new StepBuilder("importRisultatiCompetizioneStep", jobRepository)
                .<RisultatoCompetizioneBatchRecord, MatchRecord>chunk(10, transactionManager)
                .reader(risultatoCompetizioneBatchRecordItemReader)
                .processor(compositeRisultatoCompetizioneItemProcessor)
                .writer(matchRecordItemWriter)
                .build();
    }

    @Bean
    @StepScope
    public ItemReader<RisultatoCompetizioneBatchRecord> risultatoCompetizioneItemReader(@Value("#{jobParameters['filePath']}") String filePath,
                                                                                        @Value("#{jobParameters['skipRows']}") Long skipRows,
                                                                                        @Value("#{jobParameters['sheetName']}") String sheetName,
                                                                                        @Value("#{jobParameters['giornata']}") Long giornata) throws Exception {
        Resource resource = new FileSystemResource(filePath);
        Integer giornataInt = giornata != null ? giornata.intValue() : null;
        return new ImportRisultatoCompetizioneItemReader(resource, skipRows.intValue(), sheetName, giornataInt);
    }

    @Bean
    public JpaItemWriter<RisultatoCompetizione> risultatoCompetizioneItemWriter(EntityManagerFactory entityManagerFactory) {
        JpaItemWriter<RisultatoCompetizione> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }

    @Bean
    @StepScope
    public CompositeItemProcessor<RisultatoCompetizioneBatchRecord, MatchRecord> compositeRisultatoCompetizioneItemProcessor(
            ItemProcessor<RisultatoCompetizioneBatchRecord, List<RisultatoCompetizioneDto>> risultatoCompetizioneBatchRecordItemProcessor,
            ItemProcessor<List<RisultatoCompetizioneDto>, MatchRecord> risultatoCompetizioneDtoListToMatchRecordProcessor) {
        CompositeItemProcessor<RisultatoCompetizioneBatchRecord, MatchRecord> processor = new CompositeItemProcessor<>();
        processor.setDelegates(Arrays.asList(risultatoCompetizioneBatchRecordItemProcessor, risultatoCompetizioneDtoListToMatchRecordProcessor));
        return processor;
    }

    @Bean
    @StepScope
    public ItemProcessor<RisultatoCompetizioneBatchRecord, List<RisultatoCompetizioneDto>> importRisultatoCompetizioneItemProcessorComp(
            @Value("#{jobParameters['competizione']}") String competizione,
            @Value("#{jobParameters['faseCompetizione']}") String faseCompetizione,
            ApiGatewayFacade apiGatewayFacade) throws Exception {

        CompetizioneDto competizioneDto = getCompetizioneDto(apiGatewayFacade, competizione);
        StagioneCompetizioneDto stagioneCompetizioneDto = getStagioneCompetizioneDto(apiGatewayFacade, competizioneDto);
        FaseCompetizioneDto faseCompetizioneDto = getFaseCompetizioneDto(apiGatewayFacade, faseCompetizione);

        return new RisultatoCompetizioneBatchRecordItemProcessor(apiGatewayFacade, stagioneCompetizioneDto, faseCompetizioneDto);
    }

    private CompetizioneDto getCompetizioneDto(ApiGatewayFacade apiGatewayFacade, String competizione) {
        return apiGatewayFacade.getCompetizioneBySigla(competizione);
    }

    private StagioneCompetizioneDto getStagioneCompetizioneDto(ApiGatewayFacade apiGatewayFacade, CompetizioneDto competizioneDto) {
        StagioneDto stagioneDto = apiGatewayFacade.getLastStagione();
        return apiGatewayFacade.getStagioneCompetizioneByStagioneAndCompetizione(stagioneDto, competizioneDto);
    }

    private FaseCompetizioneDto getFaseCompetizioneDto(ApiGatewayFacade apiGatewayFacade, String faseCompetizione) throws Exception {
        return apiGatewayFacade.getFasiCompetizione().stream()
                .filter(f -> f.getSigla().equalsIgnoreCase(faseCompetizione))
                .findFirst()
                .orElseThrow(() -> new Exception("Fase competizione non trovata"));
    }
}
