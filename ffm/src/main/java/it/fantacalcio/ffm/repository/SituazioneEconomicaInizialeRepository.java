package it.fantacalcio.ffm.repository;

import it.fantacalcio.ffm.domain.dto.SituazioneEconomicaInizialeDto;
import it.fantacalcio.ffm.domain.entity.SituazioneEconomicaIniziale;
import it.fantacalcio.ffm.domain.entity.Squadra;
import it.fantacalcio.ffm.domain.entity.Stagione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SituazioneEconomicaInizialeRepository extends JpaRepository<SituazioneEconomicaIniziale, Integer> {
    SituazioneEconomicaInizialeDto findBySquadraAndStagione(Squadra squadra, Stagione stagione);
}