package it.fantacalcio.ffm.domain.entity;

import it.fantacalcio.ffm.converter.TipologiaListoneConverter;
import it.fantacalcio.ffm.utility.Constants;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "giocatore_listone")
public class GiocatoreListone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "ID_FANTAGAZZETTA", nullable = false)
        private Integer idFantagazzetta;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_STAGIONE", nullable = false)
    private Stagione idStagione;

    @Column(name = "FVM", nullable = false, length = 4)
    private Integer fvm;

    @Convert(converter = TipologiaListoneConverter.class)
    @Column(name = "TIPOLOGIA_LISTONE", nullable = false)
    private Constants.TipologiaListoneEnum tipologiaListone;

    @Column(name = "DATA_CREAZIONE", nullable = false, updatable = false)
    private LocalDateTime dataCreazione;

    @PrePersist
    protected void onCreate() {
        dataCreazione = LocalDateTime.now();
    }
}