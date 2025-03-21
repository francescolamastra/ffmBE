package it.fantacalcio.ffm.repository;

import it.fantacalcio.ffm.domain.dto.StagioneCompetizioneDto;
import it.fantacalcio.ffm.domain.entity.Competizione;
import it.fantacalcio.ffm.domain.entity.Stagione;
import it.fantacalcio.ffm.domain.entity.StagioneCompetizione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StagioneCompetizioneRepository extends JpaRepository<StagioneCompetizione, Integer> {
    Optional<StagioneCompetizione> findByIdStagioneAndIdCompetizione(Stagione stagione, Competizione competizione);

    List<StagioneCompetizioneDto> findAllByIdStagione(Stagione stagione);
}