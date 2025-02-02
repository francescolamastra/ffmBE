package it.fantacalcio.ffm.repository;

import it.fantacalcio.ffm.domain.entity.DettaglioTrattativa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DettaglioTrattativaRepository extends JpaRepository<DettaglioTrattativa, Integer> {
}