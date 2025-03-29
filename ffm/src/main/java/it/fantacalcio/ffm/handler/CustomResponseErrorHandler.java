package it.fantacalcio.ffm.handler;

import it.fantacalcio.ffm.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.net.URI;

public class CustomResponseErrorHandler implements ResponseErrorHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomResponseErrorHandler.class);

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().isError();
    }

    @Override
    public void handleError(URI url, HttpMethod method, ClientHttpResponse response) throws IOException {
        HttpStatusCode statusCode = response.getStatusCode();
        logger.error("Errore durante la chiamata a {} {}: {}", method, url, statusCode);
        if (statusCode.is4xxClientError()) {
            switch (statusCode.value()) {
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
        } else if (statusCode.is5xxServerError()) {
            switch (statusCode.value()) {
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
