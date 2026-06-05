package com.vozciudadana.dto;

import jakarta.validation.constraints.NotBlank;

public class FirmaDigitalRequest {
    @NotBlank
    private String firmante;

    @NotBlank
    private String cargo;

    @NotBlank
    private String certificadoReferencia;

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
}
