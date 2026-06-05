package com.vozciudadana.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HomeController {
    @GetMapping("/")
    public Map<String, String> inicio() {
        return Map.of(
                "aplicacion", "Voz del Ciudadano",
                "backend", "activo",
                "endpoint", "/api/propuestas"
        );
    }
}
