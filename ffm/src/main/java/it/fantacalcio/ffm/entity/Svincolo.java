package it.fantacalcio.ffm.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "svincolo")
public class Svincolo {
    @Id
    @Column(name = "ID", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_OPERAZIONE", nullable = false)
    private Operazione idOperazione;

    @Column(name = "PERCENTUALE", nullable = false)
    private Byte percentuale;

}