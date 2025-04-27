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
@Table(name = "situazione_economica_iniziale")
public class SituazioneEconomicaIniziale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_STAGIONE", nullable = false)
    private Stagione stagione;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_SQUADRA", nullable = false)
    private Squadra squadra;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_STADIO")
    private Stadio stadio;

    @ColumnDefault(value = "0")
    @Column(name = "CREDITI", nullable = false)
    private Integer crediti = 0;

    @ColumnDefault(value = "0")
    @Column(name = "GETTONI", nullable = false)
    private Integer gettoni = 0;
}