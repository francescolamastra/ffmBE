package it.fantacalcio.ffm.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "nazione")
public class Nazione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "DESCRIZIONE", nullable = false, length = 20)
    private String descrizione;

    @Column(name = "SIGLA", nullable = false, length = 5)
    private String sigla;

}