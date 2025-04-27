package it.fantacalcio.ffm.repository;

import it.fantacalcio.ffm.domain.entity.GiocatoreListone;
import it.fantacalcio.ffm.domain.entity.Stagione;
import it.fantacalcio.ffm.utility.Constants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GiocatoreListoneRepository extends JpaRepository<GiocatoreListone, Integer> {
    Optional<GiocatoreListone> findByIdStagioneAndIdFantagazzettaAndTipologiaListone(Stagione idStagione, Integer idFantagazzetta, Constants.TipologiaListoneEnum tipologiaListoneEnum);
}