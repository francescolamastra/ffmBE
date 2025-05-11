package it.fantacalcio.ffm.batch.config.drive;

import it.fantacalcio.ffm.batch.config.drive.sheet.SheetConfig;
import it.fantacalcio.ffm.batch.model.DriveCsvRow;
import it.fantacalcio.ffm.batch.model.GiocatoreListoneGiocatoreComposite;
import it.fantacalcio.ffm.batch.model.GiocatoreRosaOperazioneComposite;
import it.fantacalcio.ffm.batch.processor.ImportDriveCsvItemProcessor;
import it.fantacalcio.ffm.batch.utility.DriveHelper;
import it.fantacalcio.ffm.batch.writer.ImportDriveCsvItemWriter;
import it.fantacalcio.ffm.domain.dto.SquadraDto;
import it.fantacalcio.ffm.domain.dto.StagioneDto;
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
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.UrlResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class GoogleSheetBatchConfig {

    @Bean
    public Job importDriveCsvJob(JobRepository jobRepository,
                                 Step importDriveCsvStep,
                                 JobExecutionDecider importDecider,
                                 JobExecutionListener jobExecutionListener) {
        return new JobBuilder("importDriveCsvJob", jobRepository)
                .listener(jobExecutionListener)
                .incrementer(new RunIdIncrementer())
                .start(importDriveCsvStep)
                .on("*").to(importDecider)
                .from(importDecider).on("COMPLETED").end()
                .from(importDecider).on("NOT_COMPLETED").fail()
                .build().build();
    }

    @Bean
    public Step importDriveCsvStep(JobRepository jobRepository,
                                   PlatformTransactionManager transactionManager,
                                   ItemReader<DriveCsvRow> driveCsvReader,
                                   ItemProcessor<DriveCsvRow, Object> driveCsvProcessor,
                                   ItemWriter<Object> driveCsvItemWriter) {
        return new StepBuilder("importDriveCsvStep", jobRepository)
                .<DriveCsvRow, Object>chunk(10, transactionManager)
                .reader(driveCsvReader)
                .processor(driveCsvProcessor)
                .writer(driveCsvItemWriter)
                .build();
    }


    @Bean
    public ItemWriter<Object> driveCsvItemWriter(ItemWriter<GiocatoreRosaOperazioneComposite> importGiocatoreRosaJpaItemWriter,
                                                 ItemWriter<GiocatoreListoneGiocatoreComposite> importListoneJpaItemWriter) {
        return new ImportDriveCsvItemWriter(importGiocatoreRosaJpaItemWriter, importListoneJpaItemWriter);
    }

    @Bean
    @StepScope
    public ItemProcessor<DriveCsvRow, Object> driveCsvProcessor(ApiGatewayFacade apiGatewayFacade,
                                                                @Value("#{jobParameters['tipologiaSheet']}") String tipologiaSheet,
                                                                @Value("#{jobParameters['tipologiaRosa']}") String tipologiaRosa,
                                                                @Value("#{jobParameters['idSquadra']}") Long idSquadra) {
        Constants.TipoSheet tipoSheet = Constants.TipoSheet.valueOf(tipologiaSheet);
        Constants.TipologiaRosaEnum tipologiaRosaEnum = Constants.TipologiaRosaEnum.valueOf(tipologiaRosa);
        StagioneDto stagioneDto = apiGatewayFacade.getLastStagione();
        SquadraDto squadraDto = idSquadra > 0 ? apiGatewayFacade.getSquadraById(idSquadra.intValue()) : null;
        return new ImportDriveCsvItemProcessor(apiGatewayFacade, tipoSheet, stagioneDto, squadraDto, tipologiaRosaEnum);
    }

    @Bean
    @StepScope
    public FlatFileItemReader<DriveCsvRow> driveCsvReader(@Value("#{jobParameters['idSheet']}") String idSheet,
                                                          @Value("#{jobParameters['idFile']}") String idFile,
                                                          @Value("#{jobParameters['tipologiaSheet']}") String tipologiaSheet,
                                                          @Value("#{jobParameters['tipologiaRosa']}") String tipologiaRosa,
                                                          @Value("#{jobParameters['linesToSkip']}") Long linesToSkip,
                                                          @Value("#{jobParameters['linesToRead']}") Long linesToRead) throws Exception {
        Constants.TipoSheet tipoSheet = Constants.TipoSheet.valueOf(tipologiaSheet);
        Constants.TipologiaRosaEnum tipologiaRosaEnum = Constants.TipologiaRosaEnum.valueOf(tipologiaRosa);
        SheetConfig sheetConfig = DriveHelper.getInstance(tipoSheet, tipologiaRosaEnum);

        FlatFileItemReader<DriveCsvRow> reader = createFlatFileItemReader(idFile, idSheet);
        configureReader(reader, sheetConfig, linesToSkip, linesToRead);

        return reader;
    }

    private FlatFileItemReader<DriveCsvRow> createFlatFileItemReader(String idFile, String idSheet) throws Exception {
        FlatFileItemReader<DriveCsvRow> reader = new FlatFileItemReader<>();
        reader.setResource(new UrlResource("https://docs.google.com/spreadsheets/d/" + idFile + "/export?format=csv&gid=" + idSheet));
        return reader;
    }

    private void configureReader(FlatFileItemReader<DriveCsvRow> reader, SheetConfig sheetConfig, Long linesToSkip, Long linesToRead) {
        reader.setLinesToSkip(linesToSkip > 0 ? linesToSkip.intValue() : sheetConfig.linesToSkip());
        reader.setMaxItemCount(linesToRead > 0 ? linesToRead.intValue() : sheetConfig.linesToRead());
        reader.setLineMapper(createLineMapper(sheetConfig));
    }

    private DefaultLineMapper<DriveCsvRow> createLineMapper(SheetConfig sheetConfig) {
        DefaultLineMapper<DriveCsvRow> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames(sheetConfig.getColumns());
        lineMapper.setLineTokenizer(tokenizer);

        BeanWrapperFieldSetMapper<DriveCsvRow> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(DriveCsvRow.class);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }
}
