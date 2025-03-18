package it.fantacalcio.ffm.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "transazione_operazione")
public class TransazioneOperazione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_OPERAZIONE", nullable = false)
    private Operazione idOperazione;

    @Column(name = "IMPORTO", nullable = false)
    private Integer importo;

    @Column(name = "SEGNO_TRANSAZIONE", nullable = false, length = 1)
    private String segnoTransazione;

}