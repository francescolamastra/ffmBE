package it.fantacalcio.ffm.batch.config;

import it.fantacalcio.ffm.batch.model.RosaBatchRecord;
import it.fantacalcio.ffm.batch.processor.ImportRoseAndOperazioneItemProcessor;
import it.fantacalcio.ffm.domain.entity.Operazione;
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
public class ImportRoseAndOperazioneBatchConfig {

    @Bean
    public Job importRoseAndOperazioneJob(JobRepository jobRepository,
                             Step importRoseAndOperazioneStep,
                             Step deleteInputFileStep,
                             JobExecutionDecider importDecider,
                             JobExecutionListener jobExecutionListener) {
        return new JobBuilder("importRoseAndOperazioneJob", jobRepository)
                .listener(jobExecutionListener)
                .incrementer(new RunIdIncrementer())
                .start(importRoseAndOperazioneStep)
                .on("*").to(deleteInputFileStep)
                .from(deleteInputFileStep).on("*").to(importDecider)
                .from(importDecider).on("COMPLETED").end()
                .from(importDecider).on("NOT_COMPLETED").fail()
                .build().build();
    }

    @Bean
    public Step importRoseAndOperazioneStep(JobRepository jobRepository,
                                    PlatformTransactionManager transactionManager,
                                    ItemReader<RosaBatchRecord> readerRosaBatchRecord,
                                    ItemProcessor<RosaBatchRecord, Operazione> processorRosaBatchRecord,
                                    ItemWriter<Operazione> operazioneItemWriter) {
        return new StepBuilder("importRoseAndOperazioneStep", jobRepository)
                .<RosaBatchRecord, Operazione>chunk(10, transactionManager)
                .reader(readerRosaBatchRecord)
                .processor(processorRosaBatchRecord)
                .writer(operazioneItemWriter)
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
        tokenizer.setNames("squadraNazioneCategoria", "idFantagazzetta", "costoAcquisto");

        BeanWrapperFieldSetMapper<RosaBatchRecord> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(RosaBatchRecord.class);

        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }

    @Bean
    @StepScope
    public ItemProcessor<RosaBatchRecord, Operazione> processorRosaBatchRecord(ApiGatewayFacade apiGatewayFacade,
                                                                               @Value("#{jobParameters['sessioneMercato']}") String sessioneMercato) {
        Constants.SessioneMercatoOpAcquistoEnum sessioneMercatoOpAcquistoEnum = Constants.SessioneMercatoOpAcquistoEnum.valueOf(sessioneMercato);
        return new ImportRoseAndOperazioneItemProcessor(apiGatewayFacade, sessioneMercatoOpAcquistoEnum);
    }
}
