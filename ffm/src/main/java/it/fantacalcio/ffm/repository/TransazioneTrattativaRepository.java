package it.fantacalcio.ffm.repository;

import it.fantacalcio.ffm.domain.entity.Squadra;
import it.fantacalcio.ffm.domain.entity.TransazioneTrattativa;
import it.fantacalcio.ffm.domain.entity.Trattativa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransazioneTrattativaRepository extends JpaRepository<TransazioneTrattativa, Integer> {
    Optional<TransazioneTrattativa> findByIdTrattativaAndIdSquadra(Trattativa trattativa, Squadra squadra);
}