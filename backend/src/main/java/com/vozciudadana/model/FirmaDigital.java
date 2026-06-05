package com.vozciudadana.model;

import java.time.LocalDateTime;

public class FirmaDigital {
    private String firmante;
    private String cargo;
    private String certificadoReferencia;
    private String algoritmo;
    private String valorFirma;
    private String estadoValidacion;
    private LocalDateTime fechaRegistro;

    public FirmaDigital() {
    }

    public FirmaDigital(String firmante, String cargo, String certificadoReferencia) {
        this.firmante = firmante;
        this.cargo = cargo;
        this.certificadoReferencia = certificadoReferencia;
        this.fechaRegistro = LocalDateTime.now();
    }

    public String getFirmante() {
        return firmante;
    }

    public void setFirmante(String firmante) {
        this.firmante = firmante;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getCertificadoReferencia() {
        return certificadoReferencia;
    }

    public void setCertificadoReferencia(String certificadoReferencia) {
        this.certificadoReferencia = certificadoReferencia;
    }

    public String getAlgoritmo() {
        return algoritmo;
    }

    public void setAlgoritmo(String algoritmo) {
        this.algoritmo = algoritmo;
    }

    public String getValorFirma() {
        return valorFirma;
    }

    public void setValorFirma(String valorFirma) {
        this.valorFirma = valorFirma;
    }

    public String getEstadoValidacion() {
        return estadoValidacion;
    }

    public void setEstadoValidacion(String estadoValidacion) {
        this.estadoValidacion = estadoValidacion;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
