package com.vozciudadana.dto;

import jakarta.validation.constraints.NotBlank;

public class RecursoRequest {
    @NotBlank
    private String nombre;

    @NotBlank
    private String tipo;

    @NotBlank
    private String url;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
