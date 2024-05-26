package it.fantacalcio.ffm.repository;

import it.fantacalcio.ffm.domain.entity.Operazione;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperazioneRepository extends JpaRepository<Operazione, Integer> {
}