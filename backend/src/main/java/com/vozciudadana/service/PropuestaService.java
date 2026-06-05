package com.vozciudadana.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.vozciudadana.model.Comentario;
import com.vozciudadana.model.EstadoPropuesta;
import com.vozciudadana.model.Firma;
import com.vozciudadana.model.FirmaDigital;
import com.vozciudadana.model.Modificacion;
import com.vozciudadana.model.Propuesta;
import com.vozciudadana.model.Recurso;
import com.vozciudadana.repository.PropuestaRepository;
import com.vozciudadana.seguridad.CongeladorCriptografico;
import com.vozciudadana.seguridad.FirmadorDigital;

@Service
public class PropuestaService {
    public static final int LIMITE_FIRMAS = 25000;
    private final PropuestaRepository repository;
    private final CongeladorCriptografico congelador;
    private final FirmadorDigital firmadorDigital;

    public PropuestaService(PropuestaRepository repository, CongeladorCriptografico congelador, FirmadorDigital firmadorDigital) {
        this.repository = repository;
        this.congelador = congelador;
        this.firmadorDigital = firmadorDigital;
    }

    public List<Propuesta> listar() {
        return repository.findAll();
    }

    public Propuesta obtener(String id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Propuesta no encontrada"));
    }

    public Propuesta crear(Propuesta propuesta) {
        propuesta.setEstado(EstadoPropuesta.ABIERTA);
        propuesta.setFechaCreacion(LocalDateTime.now());
        propuesta.setFechaLimite(propuesta.getFechaCreacion().plusDays(90));
        return repository.save(propuesta);
    }

    public Propuesta agregarFirma(String id, Firma firma) {
        Propuesta propuesta = obtener(id);
        propuesta.getFirmas().add(firma);
        if (propuesta.cantidadFirmasValidas() >= LIMITE_FIRMAS) {
            congelarYEnviar(propuesta);
        }
        return repository.save(propuesta);
    }

    public Propuesta agregarComentario(String id, Comentario comentario) {
        Propuesta propuesta = obtener(id);
        validarAbierta(propuesta);
        propuesta.getComentarios().add(comentario);
        return repository.save(propuesta);
    }

    public Propuesta agregarRecurso(String id, Recurso recurso) {
        Propuesta propuesta = obtener(id);
        validarAbierta(propuesta);
        propuesta.getRecursos().add(recurso);
        return repository.save(propuesta);
    }

    public Propuesta agregarModificacion(String id, Modificacion modificacion) {
        Propuesta propuesta = obtener(id);
        validarAbierta(propuesta);
        propuesta.getModificaciones().add(modificacion);
        return repository.save(propuesta);
    }

    public Propuesta registrarFirmaDigital(String id, FirmaDigital firmaDigital) {
        Propuesta propuesta = obtener(id);
        if (propuesta.getHashCriptografico() == null) {
            propuesta.setHashCriptografico(congelador.generarHash(propuesta));
        }
        propuesta.setFirmaDigital(firmadorDigital.firmar(propuesta, firmaDigital));
        return repository.save(propuesta);
    }

    public Propuesta cerrarManualmente(String id) {
        Propuesta propuesta = obtener(id);
        if (propuesta.cantidadFirmasValidas() < LIMITE_FIRMAS) {
            throw new IllegalStateException("La propuesta todavía no alcanza 25000 firmas válidas");
        }
        congelarYEnviar(propuesta);
        return repository.save(propuesta);
    }

    public void validarAbierta(Propuesta propuesta) {
        if (propuesta.getEstado() != EstadoPropuesta.ABIERTA) {
            throw new IllegalStateException("La propuesta ya fue cerrada");
        }
        if (LocalDateTime.now().isAfter(propuesta.getFechaLimite())) {
            throw new IllegalStateException("El plazo de 90 días ya venció");
        }
    }

    public void congelarYEnviar(Propuesta propuesta) {
        if (propuesta.getEstado() == EstadoPropuesta.ENVIADA) {
            return;
        }
        propuesta.setHashCriptografico(congelador.generarHash(propuesta));
        propuesta.setFechaCongelamiento(LocalDateTime.now());
        propuesta.setEstado(EstadoPropuesta.ENVIADA);
        propuesta.setFechaEnvio(LocalDateTime.now());
        propuesta.setExpedienteCongreso("EXP-" + LocalDateTime.now().getYear() + "-" + Math.abs(propuesta.getId().hashCode()));
    }
}
