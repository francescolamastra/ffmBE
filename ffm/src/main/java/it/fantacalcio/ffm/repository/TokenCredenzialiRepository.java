package it.fantacalcio.ffm.repository;

import it.fantacalcio.ffm.domain.entity.TokenCredenziali;
import it.fantacalcio.ffm.domain.entity.TokenCredenzialiInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TokenCredenzialiRepository extends JpaRepository<TokenCredenziali, Integer> {
    @Query("SELECT t.jwt AS jwt, t.nazione AS nazione FROM TokenCredenziali t")
    List<TokenCredenzialiInfo> findAllTokenCredenzialiInfo();
}