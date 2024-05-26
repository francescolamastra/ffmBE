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
@Table(name = "stadio")
public class Stadio {
    @Id
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "Livello", nullable = false)
    private Integer livello;

    @Column(name = "COEFFICIENTE", nullable = false)
    private Float coefficiente;

    @Column(name = "COMPENSO_VITTORIA", nullable = false)
    private Integer compensoVittoria;

    @Column(name = "COMPENSO_PAREGGIO", nullable = false)
    private Integer compensoPareggio;

    @Column(name = "BONUS", nullable = false)
    private Float bonus;

    @Column(name = "MANUTENZIONE", nullable = false)
    private Integer manutenzione;

}