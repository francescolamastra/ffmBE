package it.fantacalcio.ffm.domain.model.fantaleghe;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class FantalegheTrattativeScambio {
    @JsonProperty("lstid")
    private String lastId;

    @JsonAlias({"oprs"})
    private List<Scambio> listaScambi;

    @Data
    public static class Scambio {
        @JsonProperty("offed")
        private Integer creditiPagatiSquadraA;

        @JsonProperty("pDate")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
        private LocalDateTime dataOperazione;

        @JsonProperty("pofed")
        private List<Integer> listGiocatoriCedutiSquadraA;

        @JsonProperty("preqe")
        private List<Integer> listGiocatoriCedutiSquadraB;

        @JsonProperty("recId")
        private Integer idSquadraB;

        @JsonProperty("reqed")
        private Integer creditiPagatiSquadraB;

        @JsonProperty("senId")
        private Integer idSquadraA;
    }
}