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
@Table(name = "giocatore")
public class Giocatore {
    @Id
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "ID_FANTAGAZZETTA", nullable = false)
    private Integer idFantagazzetta;

    @Column(name = "NOME", nullable = false, length = 50)
    private String nome;

    @Column(name = "RUOLO", nullable = false, length = 1)
    private String ruolo;

    @Column(name = "QUOTAZIONE", nullable = false)
    private Byte quotazione;

}