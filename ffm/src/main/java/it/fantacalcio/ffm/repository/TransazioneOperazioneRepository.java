package it.fantacalcio.ffm.repository;

import it.fantacalcio.ffm.domain.entity.TransazioneOperazione;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransazioneOperazioneRepository extends JpaRepository<TransazioneOperazione, Integer> {
}