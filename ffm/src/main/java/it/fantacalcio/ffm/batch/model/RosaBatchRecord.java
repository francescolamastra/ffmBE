package it.fantacalcio.ffm.batch.model;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RosaBatchRecord implements Serializable {
    String squadraNazioneCategoria;
    String idFantagazzetta;
    String costoAcquisto;
}
