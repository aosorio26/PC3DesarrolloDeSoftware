package com.vozciudadana.participacion;

import com.vozciudadana.model.Firma;
import com.vozciudadana.model.Propuesta;
import com.vozciudadana.service.PropuestaService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class ControlFirmas implements GestorFirmas {
    private final RegistroFirmas servicioReal;
    private final PropuestaService propuestaService;

    public ControlFirmas(RegistroFirmas servicioReal, PropuestaService propuestaService) {
        this.servicioReal = servicioReal;
        this.propuestaService = propuestaService;
    }

    @Override
    public Propuesta firmar(String propuestaId, Firma firma) {
        Propuesta propuesta = propuestaService.obtener(propuestaId);
        propuestaService.validarAbierta(propuesta);
        for (Firma existente : propuesta.getFirmas()) {
            if (existente.getDni().equals(firma.getDni())) {
                throw new IllegalStateException("El ciudadano ya firmó esta propuesta");
            }
        }
        return servicioReal.firmar(propuestaId, firma);
    }
}
