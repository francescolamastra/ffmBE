package it.fantacalcio.ffm.service;

import it.fantacalcio.ffm.domain.model.fantaleghe.FantalegheLoginRequest;
import it.fantacalcio.ffm.domain.model.fantaleghe.FantalegheLoginResponse;
import it.fantacalcio.ffm.domain.model.fantaleghe.FantalegheMercato;
import it.fantacalcio.ffm.domain.model.fantaleghe.FantalegheTeam;
import it.fantacalcio.ffm.utility.LegacyFantalegheHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FantalegheService {
    private final WebClient webClient;
    private final LegacyFantalegheHelper legacyFantalegheHelper;

    public FantalegheLoginResponse login(FantalegheLoginRequest loginRequest) {

        HttpHeaders headers = legacyFantalegheHelper.getBaseFantalegheHeaders();

        return webClient.post()
                .uri(legacyFantalegheHelper.fantalegheLoginUrl())
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .body(Mono.just(loginRequest), FantalegheLoginRequest.class)
                .retrieve()
                .bodyToMono(FantalegheLoginResponse.class)
                .block();
    }

    public FantalegheMercato getMercatiCategoria(String siglaCategoria, String tokenJwt) {

        HttpHeaders headers = legacyFantalegheHelper.getBaseFantalegheHeaders();
        headers.setBearerAuth(tokenJwt);
        String endpoint = legacyFantalegheHelper.fantalegheMercatiUrl() + "/" + siglaCategoria + "?";
        return webClient.get()
                .uri(endpoint)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .retrieve()
                .bodyToMono(FantalegheMercato.class)
                .block();
    }

    public List<FantalegheTeam> getTeams(String tokenJwt) {

        HttpHeaders headers = legacyFantalegheHelper.getBaseFantalegheHeaders();
        headers.setBearerAuth(tokenJwt);
        return webClient.get()
                .uri(legacyFantalegheHelper.fantalegheTeamsUrl())
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .retrieve()
                .bodyToFlux(FantalegheTeam.class)
                .collectList()
                .block();
    }
}
