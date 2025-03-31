package it.fantacalcio.ffm.domain.entity;

/**
 * Projection for {@link TokenCredenziali}
 */
public interface TokenCredenzialiProjection {
    String getJwt();
    Nazione getNazione();
    Boolean getIsValid();
}