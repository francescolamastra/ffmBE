package it.fantacalcio.ffm.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import reactor.core.publisher.Mono;

public class CustomWebClientRequestInterceptor implements ExchangeFilterFunction {

    private static final Logger logger = LoggerFactory.getLogger(CustomWebClientRequestInterceptor.class);

    @Override
    public Mono<ClientResponse> filter(ClientRequest request, ExchangeFunction next) {
        logRequest(request);
        return next.exchange(request)
                .doOnNext(this::logResponse);
    }

    private void logRequest(ClientRequest request) {
        logger.info("Request: {} {}", request.method(), request.url());
        request.headers().forEach((name, values) -> values.forEach(value -> logger.info("{}: {}", name, value)));
    }

    private void logResponse(ClientResponse response) {
        logger.info("Response Status: {}", response.statusCode());
        response.headers().asHttpHeaders().forEach((name, values) -> values.forEach(value -> logger.info("{}: {}", name, value)));
    }
}
