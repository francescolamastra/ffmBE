package it.fantacalcio.ffm.domain.entity;

import it.fantacalcio.ffm.converter.BooleanConverter;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "token_credenziali")
public class TokenCredenziali {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "JWT", nullable = false, length = 2500)
    private String jwt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_UTENTE", nullable = false)
    private Utente utente;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_NAZIONE", nullable = false)
    private Nazione nazione;

    @Convert(converter = BooleanConverter.class)
    @Column(name = "IS_VALID", nullable = false)
    private Boolean isValid = true;

    @Column(name = "DATA_CREAZIONE", nullable = false)
    private LocalDateTime dataCreazione;

    @PrePersist
    protected void onCreate() {
        dataCreazione = LocalDateTime.now();
    }

}