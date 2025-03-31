package it.fantacalcio.ffm.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
@Component
public class CookieService {
    private final Map<String, String> cookies = new ConcurrentHashMap<>();
    private LocalDateTime expires;

    public void storeCookies(ClientHttpResponse response) throws IOException {
        List<String> setCookieHeaders = response.getHeaders().get(HttpHeaders.SET_COOKIE);
        if (setCookieHeaders != null) {
            for (String header : setCookieHeaders) {
                String[] cookieParts = header.split(";");
                for (String part : cookieParts) {
                    if (part.trim().startsWith("Expires=")) {
                        String expiresStr = part.split("=")[1].trim();
                        DateTimeFormatter formatter = DateTimeFormatter.RFC_1123_DATE_TIME;
                        expires = LocalDateTime.parse(expiresStr, formatter);
                    } else {
                        String[] cookieKeyValue = part.split("=");
                        cookies.put(cookieKeyValue[0].trim(), cookieKeyValue[1].trim());
                    }
                }
            }
        }
    }

    public boolean areCookiesValid() {
        return expires != null && LocalDateTime.now().isBefore(expires);
    }

    public String getCookiesHeader() {
        if (cookies.isEmpty()) {
            return "";
        }
        StringBuilder cookieHeader = new StringBuilder();
        cookies.forEach((key, value) -> cookieHeader.append(key).append("=").append(value).append("; "));
        return cookieHeader.toString();
    }
}
