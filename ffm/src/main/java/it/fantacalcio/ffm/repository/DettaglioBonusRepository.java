package it.fantacalcio.ffm.repository;

import it.fantacalcio.ffm.domain.entity.DettaglioBonus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DettaglioBonusRepository extends JpaRepository<DettaglioBonus, Integer> {
}