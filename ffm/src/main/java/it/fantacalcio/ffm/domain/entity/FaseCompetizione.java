package it.fantacalcio.ffm.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "fase_competizione")
public class FaseCompetizione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "SIGLA", nullable = false, length = 5)
    private String sigla;

    @Column(name = "DESCRIZIONE", nullable = false, length = 20)
    private String descrizione;

}