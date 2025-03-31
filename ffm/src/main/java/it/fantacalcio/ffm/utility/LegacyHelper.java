package it.fantacalcio.ffm.utility;

import it.fantacalcio.ffm.config.ApiFantalegheProperties;
import it.fantacalcio.ffm.service.CookieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LegacyHelper {
    private final ApiFantalegheProperties apiFantalegheProperties;
    private final CookieService cookieService;

    public String fantalegheLoginUrl(){
        return apiFantalegheProperties.getLoginEndpoint();
    }

    public HttpHeaders getBaseFantalegheHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.HOST, apiFantalegheProperties.getHeaders().get("host"));
        headers.set("App_key", apiFantalegheProperties.getHeaders().get("app-key"));
        headers.set(HttpHeaders.ACCEPT, apiFantalegheProperties.getHeaders().get("accept"));
        headers.set(HttpHeaders.CONTENT_TYPE, apiFantalegheProperties.getHeaders().get("content-type"));
        headers.set(HttpHeaders.ACCEPT_ENCODING, apiFantalegheProperties.getHeaders().get("accept-encoding"));
        headers.set(HttpHeaders.USER_AGENT, apiFantalegheProperties.getHeaders().get("user-agent"));
        return headers;
    }
}
