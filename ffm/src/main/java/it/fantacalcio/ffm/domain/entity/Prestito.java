package it.fantacalcio.ffm.domain.entity;

import it.fantacalcio.ffm.converter.BooleanConverter;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Type;

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

    @Convert(converter = BooleanConverter.class)
    @Column(name = "OBBLIGO", nullable = false)
    private Boolean obbligo = false;

    @Convert(converter = BooleanConverter.class)
    @Column(name = "RISCATTO", nullable = false)
    private Boolean riscatto = false;

    @Convert(converter = BooleanConverter.class)
    @Column(name = "ESERCITATO", nullable = false)
    private Boolean esercitato = false;

}