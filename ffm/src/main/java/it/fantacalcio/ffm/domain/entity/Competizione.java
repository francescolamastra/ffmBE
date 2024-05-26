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
@Table(name = "competizione")
public class Competizione {
    @Id
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "SIGLA", nullable = false, length = 5)
    private String sigla;

    @Column(name = "DESCRIZIONE", nullable = false, length = 20)
    private String descrizione;

}