package it.fantacalcio.ffm.batch.config;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBatchTest
@SpringBootTest
@EnableAutoConfiguration(exclude = {JpaRepositoriesAutoConfiguration.class})
@ActiveProfiles("test")
public class GoogleSheetBatchConfigTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private Job importDriveCsvJob;

    @Test
    public void testImportDriveCsvJob() throws Exception {
        // Configura i parametri del job
        JobExecution jobExecution = jobLauncherTestUtils.getJobLauncher().run(
                importDriveCsvJob,
                new JobParametersBuilder()
                        .addString("filePath", "https://docs.google.com/spreadsheets/d/1Bb9GfXisOTYDnT7JCBGYXKAa3BLUthS-YpViTJSHrZw/export?format=csv")
                        .toJobParameters()
        );

        // Verifica che il job sia completato con successo
        assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("COMPLETED");
    }
}
