package it.fantacalcio.ffm.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "ffm.web.api-fantaleghe")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiFantalegheProperties {
    String domainUrl;
    Map<String, String> headers;
    String loginEndpoint;
    String teamsEndpoint;
    String mercatiConclusiEndpoint;
    String mercatiSvincoli;
    String mercatiAcquisti;
    String mercatiSvincoliAcquisti;
    String mercatiBuste;
}
