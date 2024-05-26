package it.fantacalcio.ffm.repository;

import it.fantacalcio.ffm.domain.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtenteRepository extends JpaRepository<Utente, Integer> {
}