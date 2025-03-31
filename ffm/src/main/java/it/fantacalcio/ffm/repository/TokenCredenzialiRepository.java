package it.fantacalcio.ffm.repository;

import it.fantacalcio.ffm.domain.entity.TokenCredenziali;
import it.fantacalcio.ffm.domain.entity.TokenCredenzialiProjection;
import it.fantacalcio.ffm.domain.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TokenCredenzialiRepository extends JpaRepository<TokenCredenziali, Integer> {
    @Query("SELECT t.jwt AS jwt, t.nazione AS nazione, t.isValid AS isValid FROM TokenCredenziali t WHERE t.utente = :utente")
    List<TokenCredenzialiProjection> findAllTokenCredenzialiProjectionByUtente(@Param("utente") Utente utente);
}