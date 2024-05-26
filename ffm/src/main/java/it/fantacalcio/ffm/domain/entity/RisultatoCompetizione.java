package it.fantacalcio.ffm.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "risultato_competizione")
public class RisultatoCompetizione {
    @Id
    @Column(name = "ID", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_STAGIONE_COMPETIZIONE", nullable = false)
    private StagioneCompetizione idStagioneCompetizione;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_SQUADRA", nullable = false)
    private Squadra idSquadra;

    @Column(name = "RISULTATO", nullable = false, length = 1)
    private String risultato;

    @Column(name = "GIORNATA_SERIE_A", nullable = false)
    private Byte giornataSerieA;

    @Column(name = "GIORNATA_COMPETIZIONE", nullable = false)
    private Byte giornataCompetizione;

    @Column(name = "LUOGO_RISULTATO", nullable = false, length = 1)
    private String luogoRisultato;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_FASE_COMPETIZIONE", nullable = false)
    private FaseCompetizione idFaseCompetizione;

}