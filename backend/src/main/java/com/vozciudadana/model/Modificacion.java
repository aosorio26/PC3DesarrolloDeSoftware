package com.vozciudadana.model;

import java.time.LocalDateTime;

public class Modificacion {
    private String autor;
    private String descripcion;
    private String textoPropuesto;
    private LocalDateTime fecha;

    public Modificacion() {
    }

    public Modificacion(String autor, String descripcion, String textoPropuesto) {
        this.autor = autor;
        this.descripcion = descripcion;
        this.textoPropuesto = textoPropuesto;
        this.fecha = LocalDateTime.now();
    }

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

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
