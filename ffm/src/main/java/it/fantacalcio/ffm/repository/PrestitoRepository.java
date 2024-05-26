package it.fantacalcio.ffm.repository;

import it.fantacalcio.ffm.domain.entity.Prestito;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrestitoRepository extends JpaRepository<Prestito, Integer> {
}