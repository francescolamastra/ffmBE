package it.fantacalcio.ffm.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "acquisto")
public class Acquisto {
    @Id
    @Column(name = "ID", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_OPERAZIONE", nullable = false)
    private Operazione idOperazione;

    @Column(name = "ANNI_CONTRATTO", nullable = false)
    private Integer anniContratto;

}