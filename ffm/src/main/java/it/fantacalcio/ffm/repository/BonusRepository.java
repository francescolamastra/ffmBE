package it.fantacalcio.ffm.repository;

import it.fantacalcio.ffm.domain.entity.Bonus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BonusRepository extends JpaRepository<Bonus, Integer> {
}