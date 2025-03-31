package it.fantacalcio.ffm.config;

import io.netty.channel.ChannelOption;
import it.fantacalcio.ffm.handler.CustomWebClientErrorHandler;
import it.fantacalcio.ffm.interceptor.CustomWebClientRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class WebClientConfig {

    private final Map<String, List<String>> cookies = new ConcurrentHashMap<>();

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

    private ExchangeFilterFunction cookieFilter() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            List<String> cookieHeaders = cookies.get(clientRequest.url().getHost());
            if (cookieHeaders != null) {
                clientRequest.headers().put(HttpHeaders.COOKIE, cookieHeaders);
            }
            return Mono.just(clientRequest);
        }).andThen(ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            List<String> setCookieHeaders = clientResponse.headers().asHttpHeaders().get(HttpHeaders.SET_COOKIE);
            if (setCookieHeaders != null) {
                cookies.put(clientResponse.request().getURI().getHost(), setCookieHeaders);
            }
            return Mono.just(clientResponse);
        }));
    }
}
