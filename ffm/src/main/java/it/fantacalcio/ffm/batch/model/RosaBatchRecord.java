package it.fantacalcio.ffm.batch.model;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RosaBatchRecord implements Serializable {
    String squadra;
    String idFantagazzetta;
    String costoAcquisto;
}
