package it.fantacalcio.ffm.domain.entity;

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

    @PrePersist
    protected void onCreate() {
        if(dataCreazione == null){
            dataCreazione = LocalDateTime.now();
        }
    }
}