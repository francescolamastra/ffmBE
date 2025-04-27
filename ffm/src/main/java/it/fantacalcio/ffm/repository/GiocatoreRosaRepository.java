package it.fantacalcio.ffm.repository;

import it.fantacalcio.ffm.domain.entity.Giocatore;
import it.fantacalcio.ffm.domain.entity.GiocatoreRosa;
import it.fantacalcio.ffm.domain.entity.Squadra;
import it.fantacalcio.ffm.domain.entity.Stagione;
import it.fantacalcio.ffm.utility.Constants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GiocatoreRosaRepository extends JpaRepository<GiocatoreRosa, Integer> {
    Optional<GiocatoreRosa> findByIdStagioneAndIdGiocatoreAndTipologiaRosa(Stagione idStagione, Giocatore idGiocatore, Constants.TipologiaRosaEnum tipologiaRosa);
    List<GiocatoreRosa> findAllByIdStagioneAndIdSquadraAndTipologiaRosa(Stagione idStagione, Squadra idSquadra, Constants.TipologiaRosaEnum tipologiaRosa);
    boolean existsByIdStagioneAndIdSquadraAndTipologiaRosa(Stagione idStagione, Squadra idSquadra, Constants.TipologiaRosaEnum tipologiaRosa);
    Optional<GiocatoreRosa> findByIdStagioneAndTipologiaRosa(Stagione stagione, Constants.TipologiaRosaEnum tipologiaRosa);
}