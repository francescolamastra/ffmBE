package it.fantacalcio.ffm.repository;

import it.fantacalcio.ffm.domain.entity.Stagione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StagioneRepository extends JpaRepository<Stagione, Integer> {
    Optional<Stagione> findFirstByOrderByAnnoFineDesc();
    Optional<Stagione> findByAnnoFine(Integer annoFine);
    Optional<Stagione> findByAnnoInizio(Integer annoInizio);
}