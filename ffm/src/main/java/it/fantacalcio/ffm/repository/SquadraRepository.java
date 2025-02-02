package it.fantacalcio.ffm.repository;

import it.fantacalcio.ffm.domain.entity.Squadra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SquadraRepository extends JpaRepository<Squadra, Integer> {
}