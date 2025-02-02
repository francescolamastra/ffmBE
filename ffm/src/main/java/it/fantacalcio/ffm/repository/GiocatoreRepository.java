package it.fantacalcio.ffm.repository;

import it.fantacalcio.ffm.domain.entity.Giocatore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GiocatoreRepository extends JpaRepository<Giocatore, Integer> {
    Optional<Giocatore> findByIdFantagazzetta(Integer idFantagazzetta);
}