package it.fantacalcio.ffm.handler;

import it.fantacalcio.ffm.exception.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CustomWebClientErrorHandlerTest {

    private CustomWebClientErrorHandler errorHandler;

    @BeforeEach
    public void setUp() {
        errorHandler = new CustomWebClientErrorHandler();
    }

    @Test
    public void testHandleError_400() {
        testErrorHandling(HttpStatus.BAD_REQUEST, BadRequestException.class);
    }

    @Test
    public void testHandleError_401() {
        testErrorHandling(HttpStatus.UNAUTHORIZED, UnauthorizedException.class);
    }

    @Test
    public void testHandleError_403() {
        testErrorHandling(HttpStatus.FORBIDDEN, ForbiddenException.class);
    }

    @Test
    public void testHandleError_404() {
        testErrorHandling(HttpStatus.NOT_FOUND, ResourceNotFoundException.class);
    }

    @Test
    public void testHandleError_500() {
        testErrorHandling(HttpStatus.INTERNAL_SERVER_ERROR, ServerErrorException.class);
    }

    @Test
    public void testHandleError_503() {
        testErrorHandling(HttpStatus.SERVICE_UNAVAILABLE, ServiceUnavailableException.class);
    }

    private void testErrorHandling(HttpStatus status, Class<? extends RuntimeException> expectedException) {
        // Mock ClientResponse
        ClientResponse clientResponse = mock(ClientResponse.class);
        when(clientResponse.statusCode()).thenReturn(status);
        when(clientResponse.bodyToMono(String.class)).thenReturn(Mono.just("Error body"));

        // Mock ExchangeFunction
        ExchangeFunction exchangeFunction = mock(ExchangeFunction.class);
        when(exchangeFunction.exchange(Mockito.any(ClientRequest.class))).thenReturn(Mono.just(clientResponse));

        // Create a ClientRequest
        ClientRequest clientRequest = ClientRequest.create(HttpMethod.GET, URI.create("http://example.com")).build();

        // Test the filter method
        assertThrows(expectedException, () -> errorHandler.filter(clientRequest, exchangeFunction).block());
    }
}