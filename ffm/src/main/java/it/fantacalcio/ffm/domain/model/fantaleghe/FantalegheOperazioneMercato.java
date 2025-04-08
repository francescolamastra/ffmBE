package it.fantacalcio.ffm.domain.model.fantaleghe;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class FantalegheOperazioneMercato {
    @JsonProperty("lstid")
    private String lastId;

    @JsonAlias({"oprs", "auct"})
    private List<Operation> listaOperazioni;

    @Data
    public static class Operation {
        @JsonProperty("cost")
        private int costo;

        @JsonProperty("pid")
        private int idFantagazzettaGiocatore;

        @JsonProperty("tid")
        private int idFantagazzettaSquadra;
    }
}
