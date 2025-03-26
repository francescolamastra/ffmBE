package it.fantacalcio.ffm.repository;

import it.fantacalcio.ffm.domain.entity.RisultatoCompetizione;
import it.fantacalcio.ffm.domain.entity.StagioneCompetizione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RisultatoCompetizioneRepository extends JpaRepository<RisultatoCompetizione, Integer> {
    boolean existsByStagioneCompetizioneAndGiornataSerieA(StagioneCompetizione stagioneCompetizione, Integer giornataSerieA);
}