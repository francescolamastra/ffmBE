package it.fantacalcio.ffm.repository;

import it.fantacalcio.ffm.domain.entity.Giocatore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GiocatoreRepository extends JpaRepository<Giocatore, Integer> {
}