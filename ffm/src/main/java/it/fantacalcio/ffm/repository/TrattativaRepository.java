package it.fantacalcio.ffm.repository;

import it.fantacalcio.ffm.domain.entity.Trattativa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrattativaRepository extends JpaRepository<Trattativa, Integer> {
}