package it.fantacalcio.ffm.utility;

import it.fantacalcio.ffm.config.ApiFantalegheProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LegacyFantalegheHelper {
    private final ApiFantalegheProperties apiFantalegheProperties;
    private static HttpHeaders baseHeaders;

    static {
        baseHeaders = new HttpHeaders();
    }

    public String fantalegheLoginUrl(){
        return apiFantalegheProperties.getLoginEndpoint();
    }

    public String fantalegheMercatiUrl(){
        return apiFantalegheProperties.getMercatiConclusiEndpoint();
    }

    public String fantalegheTeamsUrl(){
        return apiFantalegheProperties.getTeamsEndpoint();
    }

    public HttpHeaders getBaseFantalegheHeaders(){
        if (baseHeaders.isEmpty()) {
            baseHeaders.set(HttpHeaders.HOST, apiFantalegheProperties.getHeaders().get("host"));
            baseHeaders.set("App_key", apiFantalegheProperties.getHeaders().get("app-key"));
            baseHeaders.set(HttpHeaders.ACCEPT, apiFantalegheProperties.getHeaders().get("accept"));
            baseHeaders.set(HttpHeaders.CONTENT_TYPE, apiFantalegheProperties.getHeaders().get("content-type"));
            baseHeaders.set(HttpHeaders.ACCEPT_ENCODING, apiFantalegheProperties.getHeaders().get("accept-encoding"));
            baseHeaders.set(HttpHeaders.USER_AGENT, apiFantalegheProperties.getHeaders().get("user-agent"));
        }
        return baseHeaders;
    }
}
