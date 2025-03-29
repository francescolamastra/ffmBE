package it.fantacalcio.ffm.config;

import it.fantacalcio.ffm.handler.CustomResponseErrorHandler;
import it.fantacalcio.ffm.interceptor.CustomClientHttpRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory());
        restTemplate.setErrorHandler(customResponseErrorHandler());
        restTemplate.getInterceptors().add(new CustomClientHttpRequestInterceptor());
        return restTemplate;
    }

    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(5000); // Timeout di connessione in millisecondi
        factory.setReadTimeout(5000); // Timeout di lettura in millisecondi
        return factory;
    }

    @Bean
    public ResponseErrorHandler customResponseErrorHandler() {
        return new CustomResponseErrorHandler();
    }
}
