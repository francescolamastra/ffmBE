package it.fantacalcio.ffm.batch.config;

import it.fantacalcio.ffm.batch.model.RosaBatchRecord;
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
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class ImportRoseBatchConfig {

    @Bean
    public Job importRoseJob(JobRepository jobRepository,
                             Step importRoseStep,
                             Step deleteInputFileStep) {
        return new JobBuilder("importRoseJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(importRoseStep)
                .next(deleteInputFileStep)
                .build();
    }

    @Bean
    public Step importRoseStep(JobRepository jobRepository,
                                    PlatformTransactionManager transactionManager,
                                    ItemReader<RosaBatchRecord> readerRosaBatchRecord,
                                    ItemProcessor<RosaBatchRecord, RosaBatchRecord> processorRosaBatchRecord,
                                    ItemWriter<RosaBatchRecord> writerRosaBatchRecord) {
        return new StepBuilder("importRoseStep", jobRepository)
                .<RosaBatchRecord, RosaBatchRecord>chunk(10, transactionManager)
                .reader(readerRosaBatchRecord)
                .processor(processorRosaBatchRecord)
                .writer(writerRosaBatchRecord)
                .build();
    }

    @Bean
    @StepScope
    public FlatFileItemReader<RosaBatchRecord> readerRosaBatchRecord(@Value("#{jobParameters['filePath']}") String filePath,
                                                                     @Value("#{jobParameters['skipRows']}") Long skipRows) {
        FlatFileItemReader<RosaBatchRecord> reader = new FlatFileItemReader<>();
        reader.setResource(new FileSystemResource(filePath));
        reader.setLinesToSkip(skipRows.intValue());
        reader.setLineMapper(lineMapper());
        return reader;
    }

    @Bean
    public LineMapper<RosaBatchRecord> lineMapper() {
        DefaultLineMapper<RosaBatchRecord> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter(",");
        tokenizer.setNames("squadra", "idFantagazzetta", "costoAcquisto");

        BeanWrapperFieldSetMapper<RosaBatchRecord> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(RosaBatchRecord.class);

        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }

}
