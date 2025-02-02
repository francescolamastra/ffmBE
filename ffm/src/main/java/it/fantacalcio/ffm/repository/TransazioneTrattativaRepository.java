package it.fantacalcio.ffm.repository;

import it.fantacalcio.ffm.domain.entity.TransazioneTrattativa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransazioneTrattativaRepository extends JpaRepository<TransazioneTrattativa, Integer> {
}