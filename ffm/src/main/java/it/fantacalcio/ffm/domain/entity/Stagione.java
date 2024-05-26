package it.fantacalcio.ffm.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "stagione")
public class Stagione {
    @Id
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "ANNO_INIZIO", nullable = false)
    private Integer annoInizio;

    @Column(name = "ANNO_FINE", nullable = false)
    private Integer annoFine;

}