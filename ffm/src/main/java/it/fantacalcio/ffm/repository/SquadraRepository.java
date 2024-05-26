package it.fantacalcio.ffm.repository;

import it.fantacalcio.ffm.domain.entity.Squadra;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SquadraRepository extends JpaRepository<Squadra, Integer> {
}