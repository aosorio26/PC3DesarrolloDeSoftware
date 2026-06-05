package com.vozciudadana.recursos;

public class TipoRecursoCompartido {
    private final String codigo;
    private final String nombre;
    private final String icono;
    private final String descripcion;

    public TipoRecursoCompartido(String codigo, String nombre, String icono, String descripcion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.icono = icono;
        this.descripcion = descripcion;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getIcono() {
        return icono;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
