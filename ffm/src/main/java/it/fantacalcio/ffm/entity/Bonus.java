package it.fantacalcio.ffm.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "bonus")
public class Bonus {
    @Id
    @Column(name = "ID", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_DETT_TRATTATIVA", nullable = false)
    private DettaglioTrattativa idDettTrattativa;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_DETT_BONUS", nullable = false)
    private DettaglioBonus idDettBonus;

    @Column(name = "MASSIMALE", nullable = false)
    private Byte massimale;

    @Column(name = "IMPORTO_SINGOLO", nullable = false)
    private Byte importoSingolo;

    @Column(name = "TIPO_BONUS", nullable = false, length = 1)
    private String tipoBonus;

}