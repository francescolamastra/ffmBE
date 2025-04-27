package it.fantacalcio.ffm.batch.config;

import it.fantacalcio.ffm.batch.model.ListoneBatchRecord;
import it.fantacalcio.ffm.batch.model.GiocatoreListoneGiocatoreComposite;
import it.fantacalcio.ffm.batch.processor.ImportListoneItemProcessor;
import it.fantacalcio.ffm.batch.reader.ImportListoneItemReader;
import it.fantacalcio.ffm.batch.writer.ImportListoneJpaItemWriter;
import it.fantacalcio.ffm.domain.entity.Giocatore;
import it.fantacalcio.ffm.domain.entity.GiocatoreListone;
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
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class ImportListoneBatchConfig {

    @Bean
    public Job importListoneJob(JobRepository jobRepository,
                                Step importListoneStep,
                                Step deleteInputFileStep,
                                JobExecutionDecider importDecider,
                                JobExecutionListener jobExecutionListener) {
        return new JobBuilder("importListoneJob", jobRepository)
                .listener(jobExecutionListener)
                .incrementer(new RunIdIncrementer())
                .start(importListoneStep)
                .on("*").to(deleteInputFileStep)
                .from(deleteInputFileStep).on("*").to(importDecider)
                .from(importDecider).on("COMPLETED").end()
                .from(importDecider).on("NOT_COMPLETED").fail()
                .build().build();
    }

    @Bean
    public Step importListoneStep(JobRepository jobRepository,
                                    PlatformTransactionManager transactionManager,
                                    ItemReader<ListoneBatchRecord> itemReader,
                                    ItemProcessor<ListoneBatchRecord, GiocatoreListoneGiocatoreComposite> itemProcessor,
                                    ItemWriter<GiocatoreListoneGiocatoreComposite> customBatchRecordJpaItemWriter) {
        return new StepBuilder("importListoneStep", jobRepository)
                .<ListoneBatchRecord, GiocatoreListoneGiocatoreComposite>chunk(10, transactionManager)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(customBatchRecordJpaItemWriter)
                .build();
    }

    @Bean
    public JpaItemWriter<Giocatore> giocatoreItemWriter(EntityManagerFactory entityManagerFactory) {
        JpaItemWriter<Giocatore> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }

    @Bean
    public JpaItemWriter<GiocatoreListone> listoneItemWriter(EntityManagerFactory entityManagerFactory) {
        JpaItemWriter<GiocatoreListone> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }

    @Bean
    public ItemWriter<GiocatoreListoneGiocatoreComposite> customBatchRecordJpaItemWriter(ApiGatewayFacade apiGatewayFacade,
                                                                                         JpaItemWriter<Giocatore> giocatoreItemWriter,
                                                                                         JpaItemWriter<GiocatoreListone> listoneItemWriter) {
        return new ImportListoneJpaItemWriter(apiGatewayFacade, giocatoreItemWriter,listoneItemWriter);
    }

    @Bean
    @StepScope
    public ItemReader<ListoneBatchRecord> itemReader(@Value("#{jobParameters['filePath']}") String filePath,
                                                     @Value("#{jobParameters['skipRows']}") Long skipRows,
                                                     @Value("#{jobParameters['sheetName']}") String sheetName) throws Exception {
        Resource resource = new FileSystemResource(filePath);
        return new ImportListoneItemReader(resource, skipRows, sheetName);
    }

    @Bean
    @StepScope
    public ItemProcessor<ListoneBatchRecord, GiocatoreListoneGiocatoreComposite> itemProcessor(ApiGatewayFacade apiGatewayFacade,
                                                                                               @Value("#{jobParameters['tipologiaListone']}") String tipologiaListone) {
        Constants.TipologiaListoneEnum tipologiaListoneEnum = Constants.TipologiaListoneEnum.valueOf(tipologiaListone);
        return new ImportListoneItemProcessor(apiGatewayFacade, tipologiaListoneEnum);
    }

}
