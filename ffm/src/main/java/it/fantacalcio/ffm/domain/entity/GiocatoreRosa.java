package it.fantacalcio.ffm.domain.entity;

import it.fantacalcio.ffm.converter.TipologiaRosaConverter;
import it.fantacalcio.ffm.utility.Constants;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "giocatore_rosa")
public class GiocatoreRosa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_SQUADRA", nullable = false)
    private Squadra idSquadra;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_GIOCATORE", nullable = false)
    private Giocatore idGiocatore;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_STAGIONE", nullable = false)
    private Stagione idStagione;

    @Convert(converter = TipologiaRosaConverter.class)
    @Column(name = "TIPOLOGIA_ROSA", nullable = false)
    private Constants.TipologiaRosaEnum tipologiaRosa;

    @Column(name = "ANNI_CONTRATTO", nullable = false)
    private Integer anniContratto;

    @Column(name = "COSTO_ACQUISTO", nullable = false)
    private Integer costoAcquisto;

    @Column(name = "FVM", nullable = false)
    private Integer fvm;

}