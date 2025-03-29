package it.fantacalcio.ffm.handler;

import it.fantacalcio.ffm.exception.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class CustomResponseErrorHandlerTest {

    private CustomResponseErrorHandler errorHandler;
    private ClientHttpResponse response;

    @BeforeEach
    public void setUp() {
        errorHandler = new CustomResponseErrorHandler();
        response = Mockito.mock(ClientHttpResponse.class);
    }

    @Test
    public void testHandleError_400() throws IOException {
        when(response.getStatusCode()).thenReturn(HttpStatusCode.valueOf(400));
        assertThrows(BadRequestException.class, () -> errorHandler.handleError(URI.create("http://example.com"), HttpMethod.GET, response));
    }

    @Test
    public void testHandleError_401() throws IOException {
        when(response.getStatusCode()).thenReturn(HttpStatusCode.valueOf(401));
        assertThrows(UnauthorizedException.class, () -> errorHandler.handleError(URI.create("http://example.com"), HttpMethod.GET, response));
    }

    @Test
    public void testHandleError_403() throws IOException {
        when(response.getStatusCode()).thenReturn(HttpStatusCode.valueOf(403));
        assertThrows(ForbiddenException.class, () -> errorHandler.handleError(URI.create("http://example.com"), HttpMethod.GET, response));
    }

    @Test
    public void testHandleError_404() throws IOException {
        when(response.getStatusCode()).thenReturn(HttpStatusCode.valueOf(404));
        assertThrows(ResourceNotFoundException.class, () -> errorHandler.handleError(URI.create("http://example.com"), HttpMethod.GET, response));
    }

    @Test
    public void testHandleError_500() throws IOException {
        when(response.getStatusCode()).thenReturn(HttpStatusCode.valueOf(500));
        assertThrows(ServerErrorException.class, () -> errorHandler.handleError(URI.create("http://example.com"), HttpMethod.GET, response));
    }

    @Test
    public void testHandleError_503() throws IOException {
        when(response.getStatusCode()).thenReturn(HttpStatusCode.valueOf(503));
        assertThrows(ServiceUnavailableException.class, () -> errorHandler.handleError(URI.create("http://example.com"), HttpMethod.GET, response));
    }
}
