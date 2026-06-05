package com.vozciudadana.dto;

import jakarta.validation.constraints.NotBlank;

public class ModificacionRequest {
    @NotBlank
    private String autor;

    @NotBlank
    private String descripcion;

    @NotBlank
    private String textoPropuesto;

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTextoPropuesto() {
        return textoPropuesto;
    }

    public void setTextoPropuesto(String textoPropuesto) {
        this.textoPropuesto = textoPropuesto;
    }
}
