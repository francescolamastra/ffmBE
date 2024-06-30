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
@Table(name = "transazione_trattativa")
public class TransazioneTrattativa {
    @Id
    @Column(name = "ID", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_TRATTATIVA", nullable = false)
    private Trattativa idTrattativa;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_SQUADRA", nullable = false)
    private Squadra idSquadra;

    @Column(name = "IMPORTO", nullable = false)
    private Integer importo;

    @Column(name = "TIPO_TRANSAZIONE", nullable = false, length = 1)
    private String tipoTransazione;

    @ColumnDefault("0")
    @Column(name = "GETTONI_SPESI", nullable = false)
    private Integer gettoniSpesi;

}