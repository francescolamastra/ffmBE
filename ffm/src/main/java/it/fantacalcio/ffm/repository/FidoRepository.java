package it.fantacalcio.ffm.repository;

import it.fantacalcio.ffm.domain.entity.Fido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FidoRepository extends JpaRepository<Fido, Integer> {
}