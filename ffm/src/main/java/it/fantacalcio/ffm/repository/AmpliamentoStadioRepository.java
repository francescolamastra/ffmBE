package it.fantacalcio.ffm.repository;

import it.fantacalcio.ffm.domain.entity.AmpliamentoStadio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmpliamentoStadioRepository extends JpaRepository<AmpliamentoStadio, Integer> {
}