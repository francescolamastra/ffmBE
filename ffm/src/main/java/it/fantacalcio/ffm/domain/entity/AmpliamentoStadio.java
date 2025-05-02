package it.fantacalcio.ffm.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "ampliamento_stadio")
public class AmpliamentoStadio {
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
    @JoinColumn(name = "ID_STADIO_ARRIVO")
    private Stadio stadio;

    @ColumnDefault(value = "0")
    @Column(name = "COSTO", nullable = false)
    private Integer costo = 0;

}