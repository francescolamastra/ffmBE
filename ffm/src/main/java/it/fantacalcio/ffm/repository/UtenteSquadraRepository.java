package it.fantacalcio.ffm.repository;

import it.fantacalcio.ffm.domain.entity.UtenteSquadra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtenteSquadraRepository extends JpaRepository<UtenteSquadra, Integer> {
}