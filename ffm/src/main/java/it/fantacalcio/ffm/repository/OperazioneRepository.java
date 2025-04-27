package it.fantacalcio.ffm.repository;

import it.fantacalcio.ffm.domain.entity.*;
import it.fantacalcio.ffm.utility.Constants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperazioneRepository extends JpaRepository<Operazione, Integer> {
    boolean existsByIdStagioneAndIdSquadraAndIdGiocatoreAndIdTipoOperazioneAndSessioneMercatoIn(Stagione stagione, Squadra squadra, Giocatore giocatore, TipoOperazione tipoOperazione, List<Constants.SessioneMercatoOpAcquistoEnum> sessioneMercato);
}