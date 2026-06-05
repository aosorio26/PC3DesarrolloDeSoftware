package com.vozciudadana.model;

import java.time.LocalDateTime;

public class Recurso {
    private String nombre;
    private String tipo;
    private String icono;
    private String descripcionTipo;
    private String url;
    private LocalDateTime fechaRegistro;

    public Recurso() {
    }

    public Recurso(String nombre, String tipo, String url) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.url = url;
        this.fechaRegistro = LocalDateTime.now();
    }

    public Recurso(String nombre, String tipo, String icono, String descripcionTipo, String url) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.icono = icono;
        this.descripcionTipo = descripcionTipo;
        this.url = url;
        this.fechaRegistro = LocalDateTime.now();
    }

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

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public String getDescripcionTipo() {
        return descripcionTipo;
    }

    public void setDescripcionTipo(String descripcionTipo) {
        this.descripcionTipo = descripcionTipo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
