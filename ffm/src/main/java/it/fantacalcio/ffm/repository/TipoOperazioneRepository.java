package it.fantacalcio.ffm.repository;

import it.fantacalcio.ffm.domain.entity.TipoOperazione;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoOperazioneRepository extends JpaRepository<TipoOperazione, Integer> {
}