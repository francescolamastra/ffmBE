package it.fantacalcio.ffm.domain.model.fantaleghe;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FantalegheTeam {
    @JsonProperty("cal")
    private String joinedIdCalciatori;

    @JsonProperty("cr")
    private int creditiResidui;

    @JsonProperty("cs")
    private String joinedCostoCalciatori;

    @JsonProperty("id")
    private int idTeam;

    @JsonProperty("n")
    private String nomeTeam;

    @JsonProperty("r")
    private SintesiRosa sintesiRosa;

    @Data
    public static class SintesiRosa {
        @JsonProperty("a")
        private int attaccanti;

        @JsonProperty("c")
        private int centrocampisti;

        @JsonProperty("d")
        private int difensori;

        @JsonProperty("p")
        private int portieri;
    }
}
