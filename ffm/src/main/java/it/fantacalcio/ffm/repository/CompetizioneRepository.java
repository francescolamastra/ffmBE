package it.fantacalcio.ffm.repository;

import it.fantacalcio.ffm.domain.entity.Competizione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompetizioneRepository extends JpaRepository<Competizione, Integer> {
}