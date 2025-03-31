package it.fantacalcio.ffm.config;

import io.netty.channel.ChannelOption;
import it.fantacalcio.ffm.handler.CustomWebClientErrorHandler;
import it.fantacalcio.ffm.interceptor.CustomWebClientRequestInterceptor;
import lombok.Data;
import lombok.Getter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
@Getter
public class WebClientConfig {

    private final Map<String, List<CookieWithExpiry>> cookies = new ConcurrentHashMap<>();

    @Bean
    public WebClient webClient() {
        HttpClient httpClient = HttpClient.create()
                .responseTimeout(Duration.ofMillis(5000)) // Timeout di lettura
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000); // Timeout di connessione

        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .filter(new CustomWebClientErrorHandler())
                .filter(new CustomWebClientRequestInterceptor())
                .filter(cookieFilter())
                .build();
    }

    @Data
    static class CookieWithExpiry {
        private final String cookie;
        private final Instant expiry;
    }

    ExchangeFilterFunction cookieFilter() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            List<CookieWithExpiry> cookieHeaders = cookies.get(clientRequest.url().getHost());
            if (cookieHeaders != null) {
                List<String> validCookies = new ArrayList<>();
                Instant now = Instant.now();
                cookieHeaders.removeIf(cookieWithExpiry -> cookieWithExpiry.getExpiry().isBefore(now));
                for (CookieWithExpiry cookieWithExpiry : cookieHeaders) {
                    validCookies.add(cookieWithExpiry.getCookie());
                }
                clientRequest.headers().put(HttpHeaders.COOKIE, validCookies);
            }
            return Mono.just(clientRequest);
        }).andThen(ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            List<String> setCookieHeaders = clientResponse.headers().asHttpHeaders().get(HttpHeaders.SET_COOKIE);
            if (setCookieHeaders != null) {
                List<CookieWithExpiry> validCookies = new ArrayList<>();
                Instant now = Instant.now();
                for (String setCookieHeader : setCookieHeaders) {
                    String[] attributes = setCookieHeader.split(";");
                    String cookieValue = attributes[0].trim();
                    Instant expiry = null;
                    for (String attribute : attributes) {
                        String[] keyValue = attribute.trim().split("=", 2);
                        if (keyValue.length == 2 && keyValue[0].equalsIgnoreCase("Expires")) {
                            try {
                                expiry = Instant.from(DateTimeFormatter.RFC_1123_DATE_TIME.parse(keyValue[1].trim()));
                            } catch (DateTimeParseException e) {
                                // Gestione dell'errore di parsing
                            }
                            break;
                        }
                    }
                    if (expiry != null) {
                        validCookies.add(new CookieWithExpiry(cookieValue, expiry));
                    }
                }
                cookies.put(clientResponse.request().getURI().getHost(), validCookies);
            }
            return Mono.just(clientResponse);
        }));
    }
}