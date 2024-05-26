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
@Table(name = "categoria")
public class Categoria {
    @Id
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "DESCRIZIONE", nullable = false, length = 20)
    private String descrizione;

    @Column(name = "SIGLA", nullable = false, length = 1)
    private String sigla;

}