package com.vozciudadana.application;

import com.vozciudadana.resumen.ResumenBase;
import com.vozciudadana.resumen.ResumenConFirmas;
import com.vozciudadana.resumen.ResumenConRecursos;
import com.vozciudadana.resumen.ResumenPropuesta;
import com.vozciudadana.recursos.TipoRecursoCompartido;
import com.vozciudadana.recursos.CatalogoTiposRecurso;
import com.vozciudadana.model.Comentario;
import com.vozciudadana.model.Firma;
import com.vozciudadana.model.FirmaDigital;
import com.vozciudadana.model.Modificacion;
import com.vozciudadana.model.Propuesta;
import com.vozciudadana.model.Recurso;
import com.vozciudadana.participacion.GestorFirmas;
import com.vozciudadana.service.PropuestaService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlataformaLegislativa {
    private final PropuestaService propuestaService;
    private final GestorFirmas firmaService;
    private final CatalogoTiposRecurso catalogoTiposRecurso;

    public PlataformaLegislativa(PropuestaService propuestaService, GestorFirmas firmaService, CatalogoTiposRecurso catalogoTiposRecurso) {
        this.propuestaService = propuestaService;
        this.firmaService = firmaService;
        this.catalogoTiposRecurso = catalogoTiposRecurso;
    }

    public List<Propuesta> listarPropuestas() {
        return propuestaService.listar();
    }

    public Propuesta buscarPropuesta(String id) {
        return propuestaService.obtener(id);
    }

    public Propuesta crearPropuesta(Propuesta propuesta) {
        return propuestaService.crear(propuesta);
    }

    public Propuesta firmar(String id, Firma firma) {
        return firmaService.firmar(id, firma);
    }

    public Propuesta comentar(String id, Comentario comentario) {
        return propuestaService.agregarComentario(id, comentario);
    }

    public Propuesta agregarRecurso(String id, Recurso recurso) {
        TipoRecursoCompartido tipo = catalogoTiposRecurso.obtener(recurso.getTipo());
        recurso.setTipo(tipo.getNombre());
        recurso.setIcono(tipo.getIcono());
        recurso.setDescripcionTipo(tipo.getDescripcion());
        return propuestaService.agregarRecurso(id, recurso);
    }

    public Propuesta modificar(String id, Modificacion modificacion) {
        return propuestaService.agregarModificacion(id, modificacion);
    }

    public Propuesta registrarFirmaDigital(String id, FirmaDigital firmaDigital) {
        return propuestaService.registrarFirmaDigital(id, firmaDigital);
    }

    public Propuesta enviarAlCongreso(String id) {
        return propuestaService.cerrarManualmente(id);
    }

    public String generarResumen(String id) {
        Propuesta propuesta = propuestaService.obtener(id);
        ResumenPropuesta resumen = new ResumenBase(propuesta);
        resumen = new ResumenConFirmas(resumen, propuesta);
        resumen = new ResumenConRecursos(resumen, propuesta);
        return resumen.generar();
    }
}
