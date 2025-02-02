package it.fantacalcio.ffm.repository;

import it.fantacalcio.ffm.domain.entity.TipoDettTrattativa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoDettTrattativaRepository extends JpaRepository<TipoDettTrattativa, Integer> {
}