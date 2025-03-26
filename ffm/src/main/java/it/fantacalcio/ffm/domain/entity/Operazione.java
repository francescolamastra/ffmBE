package it.fantacalcio.ffm.domain.entity;

import it.fantacalcio.ffm.converter.SessioneMercatoOpAcquistoConverter;
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
@Table(name = "operazione")
public class Operazione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_SQUADRA", nullable = false)
    private Squadra idSquadra;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_GIOCATORE", nullable = false)
    private Giocatore idGiocatore;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_TIPO_OPERAZIONE", nullable = false)
    private TipoOperazione idTipoOperazione;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_STAGIONE", nullable = false)
    private Stagione idStagione;

    @OneToOne(mappedBy = "idOperazione", cascade = CascadeType.ALL, optional = false, orphanRemoval = true)
    private TransazioneOperazione transazione;

    @OneToOne(mappedBy = "idOperazione", cascade = CascadeType.ALL, orphanRemoval = true)
    private Acquisto acquisto;

    @OneToOne(mappedBy = "idOperazione", cascade = CascadeType.ALL, orphanRemoval = true)
    private Svincolo svincolo;

    @Convert(converter = SessioneMercatoOpAcquistoConverter.class)
    @Column(name = "SESSIONE_MERCATO", nullable = false)
    private Constants.SessioneMercatoOpAcquistoEnum sessioneMercato;

    @Column(name = "DATA_CREAZIONE", nullable = false)
    private LocalDateTime dataCreazione;

    @PrePersist
    protected void onCreate() {
        dataCreazione = LocalDateTime.now();
    }

}