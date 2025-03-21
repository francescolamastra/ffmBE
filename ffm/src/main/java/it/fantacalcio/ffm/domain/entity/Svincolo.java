package it.fantacalcio.ffm.domain.entity;

import it.fantacalcio.ffm.converter.BooleanConverter;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "svincolo")
public class Svincolo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_OPERAZIONE", nullable = false)
    private Operazione idOperazione;

    @Column(name = "PERCENTUALE", nullable = false)
    private Integer percentuale;

    @Convert(converter = BooleanConverter.class)
    @Column(name = "PRELAZIONABILE", nullable = false)
    private Boolean prelazionabile = false;

}