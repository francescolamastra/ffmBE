package it.fantacalcio.ffm.repository;

import it.fantacalcio.ffm.domain.entity.TipoOperazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoOperazioneRepository extends JpaRepository<TipoOperazione, Integer> {
    Optional<TipoOperazione> findBySiglaIgnoreCase(String sigla);
}