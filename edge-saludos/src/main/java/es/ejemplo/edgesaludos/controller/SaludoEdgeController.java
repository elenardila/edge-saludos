package es.ejemplo.edgesaludos.controller;

import es.ejemplo.edgesaludos.dto.Saludo;
import es.ejemplo.edgesaludos.service.SupabaseService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/saludos")
public class SaludoEdgeController {

    private final RestTemplate restTemplate;
    private final SupabaseService supabase;

    @Value("${backend.base-url}")
    private String baseUrl;

    public SaludoEdgeController(RestTemplate restTemplate, SupabaseService supabase) {
        this.restTemplate = restTemplate;
        this.supabase = supabase;
    }

    // ahora devuelve la suma: backend (8080) + supabase
    @GetMapping
    public ResponseEntity<List<Saludo>> listar() {
        Saludo[] fromBackend = restTemplate.getForObject(baseUrl + "/api/saludos", Saludo[].class);
        List<Saludo> a = fromBackend == null ? List.of() : Arrays.asList(fromBackend);
        List<Saludo> b = supabase.obtenerSaludos();

        List<Saludo> merged = Stream.concat(a.stream(), b.stream()).toList();
        return ResponseEntity.ok(merged);
    }

    // (opcionales para depurar)
    @GetMapping("/backend")
    public List<Saludo> soloBackend() {
        Saludo[] arr = restTemplate.getForObject(baseUrl + "/api/saludos", Saludo[].class);
        return arr == null ? List.of() : Arrays.asList(arr);
    }

    @GetMapping("/supabase")
    public List<Saludo> soloSupabase() {
        return supabase.obtenerSaludos();
    }
}
