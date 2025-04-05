package it.fantacalcio.ffm.domain.model.fantaleghe;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

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
        private String dataFineMercato;

        @JsonProperty("mLabl")
        private MarketLabel marketLabel;

        @JsonProperty("msDat")
        private String dataInizioMercato;

        @JsonProperty("mType")
        private int mType;
    }

    @Data
    public static class MarketLabel {
        @JsonProperty("title")
        private String title;
    }
}
