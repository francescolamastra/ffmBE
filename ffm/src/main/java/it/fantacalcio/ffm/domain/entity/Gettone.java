package it.fantacalcio.ffm.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "gettone")
public class Gettone {
    @Id
    @Column(name = "ID", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_SQUADRA", nullable = false)
    private Squadra idSquadra;

    @Column(name = "QUANTITA", nullable = false)
    private Integer quantita;

    @Column(name = "DATA_ACQUISTO")
    private Instant dataAcquisto;

}