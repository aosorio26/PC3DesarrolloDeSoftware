package com.vozciudadana.model;

import java.time.LocalDateTime;

public class Comentario {
    private String autor;
    private String mensaje;
    private LocalDateTime fecha;

    public Comentario() {
    }

    public Comentario(String autor, String mensaje) {
        this.autor = autor;
        this.mensaje = mensaje;
        this.fecha = LocalDateTime.now();
    }

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

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
