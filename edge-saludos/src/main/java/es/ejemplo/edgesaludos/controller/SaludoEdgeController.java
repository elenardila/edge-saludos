package es.ejemplo.edgesaludos.controller;

import es.ejemplo.edgesaludos.dto.Saludo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/saludos")
public class SaludoEdgeController {

    private final RestTemplate restTemplate;

    @Value("${backend.base-url}")
    private String baseUrl;

    public SaludoEdgeController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public ResponseEntity<List<Saludo>> listar() {
        // Llama al micro actual y reenvía la respuesta
        Saludo[] respuesta = restTemplate.getForObject(
                baseUrl + "/api/saludos", Saludo[].class);

        List<Saludo> lista = respuesta == null ? List.of() : Arrays.asList(respuesta);
        return ResponseEntity.ok(lista);
    }
}
