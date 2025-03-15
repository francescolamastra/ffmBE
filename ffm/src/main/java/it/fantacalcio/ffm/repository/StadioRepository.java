package it.fantacalcio.ffm.repository;

import it.fantacalcio.ffm.domain.entity.Stadio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StadioRepository extends JpaRepository<Stadio, Integer> {
    Optional<Stadio> findByLivello(Integer livello);
}