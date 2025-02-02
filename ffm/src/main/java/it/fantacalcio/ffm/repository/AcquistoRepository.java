package it.fantacalcio.ffm.repository;

import it.fantacalcio.ffm.domain.entity.Acquisto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcquistoRepository extends JpaRepository<Acquisto, Integer> {
}