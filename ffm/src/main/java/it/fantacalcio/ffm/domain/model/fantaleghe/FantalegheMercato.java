package it.fantacalcio.ffm.domain.model.fantaleghe;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class FantalegheMercato {
    @JsonProperty("lstid")
    private String lastId;

    @JsonProperty("mkts")
    private List<Market> listaMercati;

    @Data
    public static class Market {
        @JsonProperty("divis")
        private String categoria;

        @JsonProperty("id")
        private String idMercato;

        @JsonProperty("meDat")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmm")
        private LocalDateTime dataFineMercato;

        @JsonProperty("mLabl")
        private MarketLabel marketLabel;

        @JsonProperty("msDat")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmm")
        private LocalDateTime dataInizioMercato;

        @JsonProperty("mType")
        private int mType;
    }

    @Data
    public static class MarketLabel {
        @JsonProperty("title")
        private String title;
    }
}
