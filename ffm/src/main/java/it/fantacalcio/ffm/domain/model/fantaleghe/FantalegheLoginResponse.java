package it.fantacalcio.ffm.domain.model.fantaleghe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FantalegheLoginResponse {
    Data data;
    @lombok.Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Data {
        List<Leghe> leghe;
    }
    @lombok.Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Leghe {
        String alias;
        int id;
        int id_squadra;
        String jwt;
        String nome;
    }
}
