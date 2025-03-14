package it.fantacalcio.ffm.batch.config;

import it.fantacalcio.ffm.batch.model.ListoneBatchRecord;
import it.fantacalcio.ffm.batch.model.ListoneGiocatoreDtoWrapper;
import it.fantacalcio.ffm.batch.reader.ImportListoneItemReader;
import it.fantacalcio.ffm.batch.writer.ImportListoneJpaItemWriter;
import it.fantacalcio.ffm.domain.entity.Giocatore;
import it.fantacalcio.ffm.domain.entity.Listone;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
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
    public Job importListoneJob(JobRepository jobRepository, Step importListoneStep) {
        return new JobBuilder("importListoneJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(importListoneStep)
                .build();
    }

    @Bean
    public Step importListoneStep(JobRepository jobRepository,
                                    PlatformTransactionManager transactionManager,
                                    ItemReader<ListoneBatchRecord> itemReader,
                                    ItemProcessor<ListoneBatchRecord, ListoneGiocatoreDtoWrapper> itemProcessor,
                                    ItemWriter<ListoneGiocatoreDtoWrapper> customBatchRecordJpaItemWriter) {
        return new StepBuilder("importListoneStep", jobRepository)
                .<ListoneBatchRecord, ListoneGiocatoreDtoWrapper>chunk(10, transactionManager)
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
    public JpaItemWriter<Listone> listoneItemWriter(EntityManagerFactory entityManagerFactory) {
        JpaItemWriter<Listone> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }

    @Bean
    public ItemWriter<ListoneGiocatoreDtoWrapper> customBatchRecordJpaItemWriter(JpaItemWriter<Giocatore> giocatoreItemWriter, JpaItemWriter<Listone> listoneItemWriter) {
        return new ImportListoneJpaItemWriter(giocatoreItemWriter,listoneItemWriter);
    }

    @Bean
    @StepScope
    public ItemReader<ListoneBatchRecord> itemReader(@Value("#{jobParameters['filePath']}") String filePath,
                                                     @Value("#{jobParameters['skipRows']}") Long skipRows,
                                                     @Value("#{jobParameters['sheetName']}") String sheetName) throws Exception {
        Resource resource = new FileSystemResource(filePath);
        return new ImportListoneItemReader(resource, skipRows, sheetName);
    }

}
