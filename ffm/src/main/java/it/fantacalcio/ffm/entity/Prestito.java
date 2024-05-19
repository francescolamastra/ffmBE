package it.fantacalcio.ffm.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "prestito")
public class Prestito {
    @Id
    @Column(name = "ID", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_OPERAZIONE", nullable = false)
    private Operazione idOperazione;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_SQUADRA_CEDENTE", nullable = false)
    private Squadra idSquadraCedente;

    @ColumnDefault("0")
    @Column(name = "COSTO_RISCATTO", nullable = false)
    private Integer costoRiscatto;

    @ColumnDefault("0")
    @Column(name = "OBBLIGO", nullable = false)
    private Boolean obbligo = false;

    @ColumnDefault("0")
    @Column(name = "RISCATTO", nullable = false)
    private Boolean riscatto = false;

    @ColumnDefault("0")
    @Column(name = "ESERCITATO", nullable = false)
    private Boolean esercitato = false;

}