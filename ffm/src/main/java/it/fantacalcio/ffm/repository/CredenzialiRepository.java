package it.fantacalcio.ffm.repository;

import it.fantacalcio.ffm.domain.entity.Credenziali;
import it.fantacalcio.ffm.domain.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CredenzialiRepository extends JpaRepository<Credenziali, Integer> {
    Optional<Credenziali> findByIdUtente(Utente utente);
}