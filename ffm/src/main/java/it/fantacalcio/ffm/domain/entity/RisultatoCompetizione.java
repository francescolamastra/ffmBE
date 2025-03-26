package it.fantacalcio.ffm.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "risultato_competizione")
public class RisultatoCompetizione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_STAGIONE_COMPETIZIONE", nullable = false)
    private StagioneCompetizione stagioneCompetizione;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_SQUADRA", nullable = false)
    private Squadra squadra;

    @Column(name = "RISULTATO", nullable = false, length = 1)
    private String risultato;

    @Column(name = "GIORNATA_SERIE_A", nullable = false)
    private Integer giornataSerieA;

    @Column(name = "GIORNATA_COMPETIZIONE", nullable = false)
    private Integer giornataCompetizione;

    @Column(name = "LUOGO_RISULTATO", nullable = false, length = 1)
    private String luogoRisultato;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_FASE_COMPETIZIONE", nullable = false)
    private FaseCompetizione faseCompetizione;

    @ColumnDefault("0")
    @Column(name = "BONUS_PUNTI", nullable = false)
    private int bonusPunti;

}