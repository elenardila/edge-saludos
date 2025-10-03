package es.ejemplo.edgesaludos.service;

import es.ejemplo.edgesaludos.dto.Saludo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class SupabaseService {

    private final RestTemplate restTemplate;

    @Value("${supabase.url}")
    private String baseUrl;

    @Value("${supabase.table:saludos_extra}")
    private String table;

    @Value("${supabase.service-key}")
    private String serviceKey;

    public SupabaseService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Saludo> obtenerSaludos() {
        String url = baseUrl + "/rest/v1/" + table + "?select=*";

        HttpHeaders headers = new HttpHeaders();
        headers.set("apikey", serviceKey);
        headers.setBearerAuth(serviceKey);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<Void> req = new HttpEntity<>(headers);

        ResponseEntity<Saludo[]> resp = restTemplate.exchange(
                url, HttpMethod.GET, req, Saludo[].class
        );

        Saludo[] body = resp.getBody();
        return body == null ? List.of() : Arrays.asList(body);
    }
}
