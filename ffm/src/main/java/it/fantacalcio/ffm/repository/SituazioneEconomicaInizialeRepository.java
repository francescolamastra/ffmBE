package it.fantacalcio.ffm.repository;

import it.fantacalcio.ffm.domain.entity.SituazioneEconomicaIniziale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SituazioneEconomicaInizialeRepository extends JpaRepository<SituazioneEconomicaIniziale, Integer> {
}