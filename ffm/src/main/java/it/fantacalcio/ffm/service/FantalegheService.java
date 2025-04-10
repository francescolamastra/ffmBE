package it.fantacalcio.ffm.service;

import it.fantacalcio.ffm.domain.model.fantaleghe.*;
import it.fantacalcio.ffm.utility.Constants;
import it.fantacalcio.ffm.utility.LegacyFantalegheHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FantalegheService {
    private final WebClient webClient;
    private final LegacyFantalegheHelper legacyFantalegheHelper;

    public FantalegheLoginResponse login(FantalegheLoginRequest loginRequest) {

        HttpHeaders headers = legacyFantalegheHelper.getBaseFantalegheHeaders();

        return webClient.post()
            .uri(uriBuilder -> uriBuilder
                .path(legacyFantalegheHelper.fantalegheLoginUrl())
                .build())
            .headers(httpHeaders -> httpHeaders.addAll(headers))
            .body(Mono.just(loginRequest), FantalegheLoginRequest.class)
            .retrieve()
            .bodyToMono(FantalegheLoginResponse.class)
            .block();
    }

    public FantalegheMercato getMercatiCategoria(String siglaCategoria, String tokenJwt) {

        HttpHeaders headers = legacyFantalegheHelper.getBaseFantalegheHeaders();
        headers.setBearerAuth(tokenJwt);

        return webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path(legacyFantalegheHelper.fantalegheMercatiUrl())
                .pathSegment(siglaCategoria)
                .build())
            .headers(httpHeaders -> httpHeaders.addAll(headers))
            .retrieve()
            .bodyToMono(FantalegheMercato.class)
            .block();
    }

    public List<FantalegheTeam> getTeams(String tokenJwt) {

        HttpHeaders headers = legacyFantalegheHelper.getBaseFantalegheHeaders();
        headers.setBearerAuth(tokenJwt);

        return webClient.get()
            .uri(uriBuilder ->  uriBuilder
                .path(legacyFantalegheHelper.fantalegheTeamsUrl())
                .build())
            .headers(httpHeaders -> httpHeaders.addAll(headers))
            .retrieve()
            .bodyToFlux(FantalegheTeam.class)
            .collectList()
            .block();
    }

    public FantalegheOperazioneMercato getOperazioniMercato(String siglaCategoria, String idMercato, Constants.TipologiaMercatoFantalegheEnum tipoMercato, String tokenJwt) {
        HttpHeaders headers = legacyFantalegheHelper.getBaseFantalegheHeaders();
        headers.setBearerAuth(tokenJwt);
        List<FantalegheOperazioneMercato.OperazioneMercato> listOperazioni = new ArrayList<>();
        String lastId = null;
        FantalegheOperazioneMercato operazioniMercato;

        do {
            String finalLastId = lastId;
            operazioniMercato = webClient.get()
                    .uri(uriBuilder -> {
                        UriBuilder builder = uriBuilder
                                .path(legacyFantalegheHelper.fantalegheOperazioniMercatoUrl(tipoMercato))
                                .pathSegment(siglaCategoria, idMercato);
                        if (finalLastId != null) {
                            builder.queryParam("lastId", finalLastId);
                        }
                        return builder.build();
                    })
                    .headers(httpHeaders -> httpHeaders.addAll(headers))
                    .retrieve()
                    .bodyToMono(FantalegheOperazioneMercato.class)
                    .block();
            lastId = operazioniMercato.getLastId();
            listOperazioni.addAll(operazioniMercato.getListaOperazioni());
        } while (lastId != null);

        operazioniMercato.setListaOperazioni(listOperazioni);
        return operazioniMercato;
    }

    public FantalegheTrattativeScambio getTrattativeScambio(String siglaCategoria, String idMercato, Constants.TipologiaMercatoFantalegheEnum tipoMercato, String tokenJwt) {
        HttpHeaders headers = legacyFantalegheHelper.getBaseFantalegheHeaders();
        headers.setBearerAuth(tokenJwt);
        List<FantalegheTrattativeScambio.Scambio> listScambi = new ArrayList<>();
        String lastId = null;
        FantalegheTrattativeScambio trattativeScambio;

        do {
            String finalLastId = lastId;
            trattativeScambio = webClient.get()
                    .uri(uriBuilder -> {
                        UriBuilder builder = uriBuilder
                                .path(legacyFantalegheHelper.fantalegheOperazioniMercatoUrl(tipoMercato))
                                .pathSegment(siglaCategoria, idMercato);
                        if (finalLastId != null) {
                            builder.queryParam("lastId", finalLastId);
                        }
                        return builder.build();
                    })
                    .headers(httpHeaders -> httpHeaders.addAll(headers))
                    .retrieve()
                    .bodyToMono(FantalegheTrattativeScambio.class)
                    .block();
            lastId = trattativeScambio.getLastId();
            listScambi.addAll(trattativeScambio.getListaScambi());
        } while (lastId != null);

        trattativeScambio.setListaScambi(listScambi);
        return trattativeScambio;
    }
}
