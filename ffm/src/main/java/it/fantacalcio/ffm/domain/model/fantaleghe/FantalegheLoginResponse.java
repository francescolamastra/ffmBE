package it.fantacalcio.ffm.domain.model.fantaleghe;

import lombok.Value;

import java.util.List;

@Value
public class FantalegheLoginResponse {
    Data data;
    @Value
    public static class Data {
        List<Leghe> leghe;
    }
    @Value
    public static class Leghe {
        String alias;
        int id;
        int id_squadra;
        String jwt;
        String nome;
    }
}
