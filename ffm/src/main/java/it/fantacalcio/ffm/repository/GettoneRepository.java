package it.fantacalcio.ffm.repository;

import it.fantacalcio.ffm.domain.entity.Gettone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GettoneRepository extends JpaRepository<Gettone, Integer> {
}