package it.fantacalcio.ffm.service;

import it.fantacalcio.ffm.domain.model.FantalegheLoginRequest;
import it.fantacalcio.ffm.domain.model.FantalegheLoginResponse;
import it.fantacalcio.ffm.utility.LegacyHelper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class FantalegheLoginService {
    private static final Logger logger = LoggerFactory.getLogger(FantalegheLoginService.class);
    private final WebClient webClient;
    private final LegacyHelper legacyHelper;

    public FantalegheLoginResponse login(FantalegheLoginRequest loginRequest) {

        HttpHeaders headers = legacyHelper.getBaseFantalegheHeaders();

        return webClient.post()
                .uri(legacyHelper.fantalegheLoginUrl())
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .body(Mono.just(loginRequest), FantalegheLoginRequest.class)
                .retrieve()
                .bodyToMono(FantalegheLoginResponse.class)
                .block();
    }
}
