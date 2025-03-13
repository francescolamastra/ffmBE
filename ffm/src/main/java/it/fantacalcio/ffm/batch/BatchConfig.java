package it.fantacalcio.ffm.batch;

import it.fantacalcio.ffm.batch.reader.ExcelItemReader;
import it.fantacalcio.ffm.domain.dto.GiocatoreDto;
import it.fantacalcio.ffm.domain.entity.Giocatore;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Bean
    public Job importGiocatoriJob(JobRepository jobRepository, Step importGiocatoriStep) {
        return new JobBuilder("importGiocatoriJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(importGiocatoriStep)
                .build();
    }

    @Bean
    public Step importGiocatoriStep(JobRepository jobRepository,
                      PlatformTransactionManager transactionManager,
                      ItemReader<GiocatoreDto> itemReader,
                      ItemProcessor<GiocatoreDto,Giocatore> itemProcessor,
                      ItemWriter<Giocatore> itemWriter) {
        return new StepBuilder("importGiocatoriStep", jobRepository)
                .<GiocatoreDto, Giocatore>chunk(10, transactionManager)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();
    }

    @Bean
    public JdbcBatchItemWriter<Giocatore> itemWriter(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Giocatore>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO GIOCATORE (ID_FANTAGAZZETTA, NOME, RUOLO) VALUES (:idFantagazzetta, :nome, :ruolo)")
                .dataSource(dataSource)
                .build();
    }

    @Bean
    @StepScope
    public ExcelItemReader itemReader(@Value("#{jobParameters['filePath']}") String filePath,
                                      @Value("#{jobParameters['skipRows'] != null ? jobParameters['skipRows'] : ${ffm.batch.excel-giocatori.skip-rows}}") int skipRows,
                                      @Value("#{jobParameters['sheetName'] != null ? jobParameters['sheetName'] : '${ffm.batch.excel-giocatori.sheet-name}'}") String sheetName) throws Exception {
        Resource resource = new FileSystemResource(filePath);
        return new ExcelItemReader(resource, skipRows, sheetName);
    }

}