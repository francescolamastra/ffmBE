package it.fantacalcio.ffm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories
@EntityScan(basePackages = "it.fantacalcio.ffm.domain.entity")
public class FfmApplication {

	public static void main(String[] args) {
		SpringApplication.run(FfmApplication.class, args);
	}

}
