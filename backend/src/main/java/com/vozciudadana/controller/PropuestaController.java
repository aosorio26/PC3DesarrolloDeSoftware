package com.vozciudadana.controller;

import com.vozciudadana.dto.ComentarioRequest;
import com.vozciudadana.dto.FirmaDigitalRequest;
import com.vozciudadana.dto.FirmaRequest;
import com.vozciudadana.dto.ModificacionRequest;
import com.vozciudadana.dto.RecursoRequest;
import com.vozciudadana.application.PlataformaLegislativa;
import com.vozciudadana.model.Comentario;
import com.vozciudadana.model.Firma;
import com.vozciudadana.model.FirmaDigital;
import com.vozciudadana.model.Modificacion;
import com.vozciudadana.model.Propuesta;
import com.vozciudadana.model.Recurso;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/propuestas")
@CrossOrigin(origins = "*")
public class PropuestaController {
    private final PlataformaLegislativa plataforma;

    public PropuestaController(PlataformaLegislativa plataforma) {
        this.plataforma = plataforma;
    }

    @GetMapping
    public List<Propuesta> listar() {
        return plataforma.listarPropuestas();
    }

    @GetMapping("/{id}")
    public Propuesta obtener(@PathVariable String id) {
        return plataforma.buscarPropuesta(id);
    }

    @PostMapping
    public Propuesta crear(@Valid @RequestBody Propuesta propuesta) {
        return plataforma.crearPropuesta(propuesta);
    }

    @PostMapping("/{id}/firmas")
    public Propuesta firmar(@PathVariable String id, @Valid @RequestBody FirmaRequest request) {
        return plataforma.firmar(id, new Firma(request.getDni(), request.getNombres(), request.getCorreo()));
    }

    @PostMapping("/{id}/comentarios")
    public Propuesta comentar(@PathVariable String id, @Valid @RequestBody ComentarioRequest request) {
        return plataforma.comentar(id, new Comentario(request.getAutor(), request.getMensaje()));
    }

    @PostMapping("/{id}/recursos")
    public Propuesta agregarRecurso(@PathVariable String id, @Valid @RequestBody RecursoRequest request) {
        return plataforma.agregarRecurso(id, new Recurso(request.getNombre(), request.getTipo(), request.getUrl()));
    }

    @PostMapping("/{id}/modificaciones")
    public Propuesta modificar(@PathVariable String id, @Valid @RequestBody ModificacionRequest request) {
        return plataforma.modificar(id, new Modificacion(request.getAutor(), request.getDescripcion(), request.getTextoPropuesto()));
    }

    @PostMapping("/{id}/firma-digital")
    public Propuesta firmaDigital(@PathVariable String id, @Valid @RequestBody FirmaDigitalRequest request) {
        return plataforma.registrarFirmaDigital(id, new FirmaDigital(request.getFirmante(), request.getCargo(), request.getCertificadoReferencia()));
    }

    @PostMapping("/{id}/enviar")
    public Propuesta enviar(@PathVariable String id) {
        return plataforma.enviarAlCongreso(id);
    }

    @GetMapping("/{id}/resumen")
    public Map<String, String> resumen(@PathVariable String id) {
        return Map.of("resumen", plataforma.generarResumen(id));
    }

    @GetMapping("/salud")
    public Map<String, String> salud() {
        return Map.of("estado", "activo");
    }
}
