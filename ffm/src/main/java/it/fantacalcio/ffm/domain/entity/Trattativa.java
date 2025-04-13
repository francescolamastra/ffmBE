package it.fantacalcio.ffm.domain.entity;

import it.fantacalcio.ffm.converter.SessioneMercatoTrattativeScambioConverter;
import it.fantacalcio.ffm.utility.Constants;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "trattativa")
public class Trattativa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_STAGIONE", nullable = false)
    private Stagione idStagione;

    @Column(name = "DATA_CREAZIONE", nullable = false)
    private LocalDateTime dataCreazione;

    @Column(name = "CLAUSOLE")
    private String clausole;

    @Convert(converter = SessioneMercatoTrattativeScambioConverter.class)
    @Column(name = "SESSIONE_MERCATO", nullable = false)
    private Constants.SessioneMercatoTrattiveScambioEnum sessioneMercato;

    @PrePersist
    protected void onCreate() {
        if(dataCreazione == null){
            dataCreazione = LocalDateTime.now();
        }
    }
}