package it.fantacalcio.ffm.repository;

import it.fantacalcio.ffm.domain.entity.Credenziali;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CredenzialiRepository extends JpaRepository<Credenziali, Integer> {
}