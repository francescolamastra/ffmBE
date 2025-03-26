package it.fantacalcio.ffm.repository;

import it.fantacalcio.ffm.domain.entity.FaseCompetizione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FaseCompetizioneRepository extends JpaRepository<FaseCompetizione, Integer> {
    Optional<FaseCompetizione> findBySigla(String sigla);
}