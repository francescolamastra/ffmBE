package it.fantacalcio.ffm.repository;

import it.fantacalcio.ffm.domain.entity.Nazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NazioneRepository extends JpaRepository<Nazione, Integer> {
    Optional<Nazione> findBySigla(String sigla);
}
