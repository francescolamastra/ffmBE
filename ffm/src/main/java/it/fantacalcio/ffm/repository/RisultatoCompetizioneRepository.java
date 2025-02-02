package it.fantacalcio.ffm.repository;

import it.fantacalcio.ffm.domain.entity.RisultatoCompetizione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RisultatoCompetizioneRepository extends JpaRepository<RisultatoCompetizione, Integer> {
}