package it.fantacalcio.ffm;

import it.fantacalcio.ffm.config.ApiFantalegheProperties;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableBatchProcessing
@EnableTransactionManagement
@EnableJpaRepositories
@EntityScan(basePackages = "it.fantacalcio.ffm.domain.entity")
@EnableConfigurationProperties(ApiFantalegheProperties.class)
public class FfmApplication {

	public static void main(String[] args) {
		SpringApplication.run(FfmApplication.class, args);
	}

}
