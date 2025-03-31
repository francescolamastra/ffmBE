package it.fantacalcio.ffm.handler;

import it.fantacalcio.ffm.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import reactor.core.publisher.Mono;

import java.net.URI;

public class CustomWebClientErrorHandler implements ExchangeFilterFunction {

    private static final Logger logger = LoggerFactory.getLogger(CustomWebClientErrorHandler.class);

    @Override
    public Mono<ClientResponse> filter(ClientRequest request, ExchangeFunction next) {
        return next.exchange(request)
                .flatMap(response -> {
                    if (response.statusCode().isError()) {
                        return response.bodyToMono(String.class)
                                .flatMap(errorBody -> {
                                    handleError(request.url(), request.method(), response.statusCode().value(), errorBody);
                                    return Mono.error(new RuntimeException("Errore: " + response.statusCode() + " - " + errorBody));
                                });
                    }
                    return Mono.just(response);
                });
    }

    private void handleError(URI url, HttpMethod method, int statusCode, String errorBody) {
        logger.error("Errore durante la chiamata a {} {}: {}", method, url, statusCode);
        if (statusCode >= 400 && statusCode < 500) {
            switch (statusCode) {
                case 400:
                    throw new BadRequestException("Richiesta non valida");
                case 401:
                    throw new UnauthorizedException("Non autorizzato");
                case 403:
                    throw new ForbiddenException("Accesso vietato");
                case 404:
                    throw new ResourceNotFoundException("Risorsa non trovata");
                case 409:
                    throw new ConflictException("Conflitto");
                default:
                    throw new GenericErrorException("Errore client: " + statusCode);
            }
        } else if (statusCode >= 500 && statusCode < 600) {
            switch (statusCode) {
                case 500:
                    throw new ServerErrorException("Errore interno del server");
                case 503:
                    throw new ServiceUnavailableException("Servizio non disponibile");
                default:
                    throw new GenericErrorException("Errore server: " + statusCode);
            }
        } else {
            throw new GenericErrorException("Errore generico: " + statusCode);
        }
    }
}
