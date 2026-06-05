package com.vozciudadana.model;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "propuestas")
public class Propuesta {
    @Id
    private String id;

    @NotBlank
    private String titulo;

    @NotBlank
    private String colectivo;

    @NotBlank
    private String resumen;

    @NotBlank
    private String textoNormativo;

    private String categoria;
    private EstadoPropuesta estado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaLimite;
    private LocalDateTime fechaCongelamiento;
    private LocalDateTime fechaEnvio;
    private String hashCriptografico;
    private String expedienteCongreso;
    private FirmaDigital firmaDigital;
    private List<Firma> firmas = new ArrayList<>();
    private List<Comentario> comentarios = new ArrayList<>();
    private List<Recurso> recursos = new ArrayList<>();
    private List<Modificacion> modificaciones = new ArrayList<>();

    public Propuesta() {
        this.estado = EstadoPropuesta.ABIERTA;
        this.fechaCreacion = LocalDateTime.now();
        this.fechaLimite = this.fechaCreacion.plusDays(90);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getColectivo() {
        return colectivo;
    }

    public void setColectivo(String colectivo) {
        this.colectivo = colectivo;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public String getTextoNormativo() {
        return textoNormativo;
    }

    public void setTextoNormativo(String textoNormativo) {
        this.textoNormativo = textoNormativo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public EstadoPropuesta getEstado() {
        return estado;
    }

    public void setEstado(EstadoPropuesta estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(LocalDateTime fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public LocalDateTime getFechaCongelamiento() {
        return fechaCongelamiento;
    }

    public void setFechaCongelamiento(LocalDateTime fechaCongelamiento) {
        this.fechaCongelamiento = fechaCongelamiento;
    }

    public LocalDateTime getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(LocalDateTime fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public String getHashCriptografico() {
        return hashCriptografico;
    }

    public void setHashCriptografico(String hashCriptografico) {
        this.hashCriptografico = hashCriptografico;
    }

    public String getExpedienteCongreso() {
        return expedienteCongreso;
    }

    public void setExpedienteCongreso(String expedienteCongreso) {
        this.expedienteCongreso = expedienteCongreso;
    }

    public FirmaDigital getFirmaDigital() {
        return firmaDigital;
    }

    public void setFirmaDigital(FirmaDigital firmaDigital) {
        this.firmaDigital = firmaDigital;
    }

    public List<Firma> getFirmas() {
        return firmas;
    }

    public void setFirmas(List<Firma> firmas) {
        this.firmas = firmas;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    public List<Recurso> getRecursos() {
        return recursos;
    }

    public void setRecursos(List<Recurso> recursos) {
        this.recursos = recursos;
    }

    public List<Modificacion> getModificaciones() {
        return modificaciones;
    }

    public void setModificaciones(List<Modificacion> modificaciones) {
        this.modificaciones = modificaciones;
    }

    public int cantidadFirmasValidas() {
        int total = 0;
        for (Firma firma : firmas) {
            if (firma.isValida()) {
                total++;
            }
        }
        return total;
    }
}
