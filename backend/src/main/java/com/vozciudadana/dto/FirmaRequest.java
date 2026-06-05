package com.vozciudadana.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class FirmaRequest {
    @NotBlank
    @Pattern(regexp = "[0-9]{8}")
    private String dni;

    @NotBlank
    private String nombres;

    @NotBlank
    @Email
    private String correo;

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
}
