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
@Table(name = "prestito")
public class Prestito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_DETT_TRATTATIVA", nullable = false)
    private DettaglioTrattativa idDettTrattativa;

    @ColumnDefault("0")
    @Column(name = "COSTO_RISCATTO", nullable = false)
    private Integer costoRiscatto;

    @Column(name = "OBBLIGO", nullable = false)
    private Boolean obbligo = false;

    @Column(name = "RISCATTO", nullable = false)
    private Boolean riscatto = false;

    @Column(name = "ESERCITATO", nullable = false)
    private Boolean esercitato = false;

}