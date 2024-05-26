package it.fantacalcio.ffm;

import it.fantacalcio.ffm.domain.entity.Nazione;
import it.fantacalcio.ffm.repository.NazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;

@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories
public class FfmApplication implements CommandLineRunner {

	@Autowired
	NazioneRepository nazioneRepository;

	public static void main(String[] args) {
		SpringApplication.run(FfmApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		nazioneRepository.findAll().forEach(
                System.out::println
		);
	}
}
