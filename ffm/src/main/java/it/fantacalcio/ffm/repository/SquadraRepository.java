package it.fantacalcio.ffm.repository;

import it.fantacalcio.ffm.domain.entity.Squadra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SquadraRepository extends JpaRepository<Squadra, Integer> {
    Optional<Squadra> findByNome(String nome);
    Optional<Squadra> findByIdFantagazzetta(Integer idFantagazzetta);
}