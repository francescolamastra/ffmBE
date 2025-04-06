package it.fantacalcio.ffm.repository;

import it.fantacalcio.ffm.domain.entity.Competizione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompetizioneRepository extends JpaRepository<Competizione, Integer> {
    Optional<Competizione> findBySiglaIgnoreCase(String sigla);
}