package it.fantacalcio.ffm.domain.entity;

/**
 * Projection for {@link TokenCredenziali}
 */
public interface TokenCredenzialiInfo {
    String getJwt();
    Nazione getNazione();
}