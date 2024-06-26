package it.fantacalcio.ffm.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "transazione_operazione")
public class TransazioneOperazione {
    @Id
    @Column(name = "ID", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_OPERAZIONE", nullable = false)
    private Operazione idOperazione;

    @Column(name = "IMPORTO", nullable = false)
    private Byte importo;

    @Column(name = "TIPO_TRANSAZIONE", nullable = false, length = 1)
    private String tipoTransazione;

}