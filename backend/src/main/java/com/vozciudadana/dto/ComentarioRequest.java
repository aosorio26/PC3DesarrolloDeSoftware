package com.vozciudadana.dto;

import jakarta.validation.constraints.NotBlank;

public class ComentarioRequest {
    @NotBlank
    private String autor;

    @NotBlank
    private String mensaje;

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
