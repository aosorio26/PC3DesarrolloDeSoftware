package com.vozciudadana.model;

import java.time.LocalDateTime;

public class Firma {
    private String dni;
    private String nombres;
    private String correo;
    private LocalDateTime fechaFirma;
    private boolean valida;

    public Firma() {
    }

    public Firma(String dni, String nombres, String correo) {
        this.dni = dni;
        this.nombres = nombres;
        this.correo = correo;
        this.fechaFirma = LocalDateTime.now();
        this.valida = true;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public LocalDateTime getFechaFirma() {
        return fechaFirma;
    }

    public void setFechaFirma(LocalDateTime fechaFirma) {
        this.fechaFirma = fechaFirma;
    }

    public boolean isValida() {
        return valida;
    }

    public void setValida(boolean valida) {
        this.valida = valida;
    }
}
