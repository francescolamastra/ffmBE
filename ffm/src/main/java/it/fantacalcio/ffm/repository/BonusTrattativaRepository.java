package it.fantacalcio.ffm.repository;

import it.fantacalcio.ffm.domain.entity.BonusTrattativa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BonusTrattativaRepository extends JpaRepository<BonusTrattativa, Integer> {
}