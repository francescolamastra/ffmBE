package it.fantacalcio.ffm.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "giocatore")
public class Giocatore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "ID_FANTAGAZZETTA", nullable = false)
    private Integer idFantagazzetta;

    @Column(name = "NOME", nullable = false, length = 50)
    private String nome;

    @Column(name = "RUOLO", nullable = false, length = 1)
    private String ruolo;

}