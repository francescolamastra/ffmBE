package it.fantacalcio.ffm.service;

import it.fantacalcio.ffm.domain.model.FantalegheLoginRequest;
import it.fantacalcio.ffm.domain.model.FantalegheLoginResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class FantalegheLoginService {
    private static final Logger logger = LoggerFactory.getLogger(FantalegheLoginService.class);
    private final RestTemplate restTemplate;

    @Autowired
    public FantalegheLoginService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public FantalegheLoginResponse login(FantalegheLoginRequest loginRequest) {
        String url = "https://myDomani/onboarding/v1/login";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("App_Key", "0a1b2c3d4e5f6a7b8c9d0e1f2a3b4c5d6e7f8a");
        headers.set("Content-Type", "application/json");

        HttpEntity<FantalegheLoginRequest> requestEntity = new HttpEntity<>(loginRequest, headers);

        try {
            ResponseEntity<FantalegheLoginResponse> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, FantalegheLoginResponse.class);
            FantalegheLoginResponse response = responseEntity.getBody();
            logger.info("Response from external endpoint: {}", response);
            return response;
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            // Gestione degli errori del client o del server
            logger.error("Error response from external endpoint: {} {}", e.getStatusCode(), e.getResponseBodyAsString());
            throw e; // Rilancia l'eccezione o gestiscila come necessario
        } catch (RestClientException e) {
            // Gestione di altri errori di RestTemplate
            logger.error("Error during external endpoint call: {}", e.getMessage());
            throw e; // Rilancia l'eccezione o gestiscila come necessario
        }
    }
}
