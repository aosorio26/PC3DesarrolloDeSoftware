package com.vozciudadana.participacion;

import com.vozciudadana.model.Firma;
import com.vozciudadana.model.Propuesta;
import com.vozciudadana.service.PropuestaService;
import org.springframework.stereotype.Service;

@Service
public class RegistroFirmas implements GestorFirmas {
    private final PropuestaService propuestaService;

    public RegistroFirmas(PropuestaService propuestaService) {
        this.propuestaService = propuestaService;
    }

    @Override
    public Propuesta firmar(String propuestaId, Firma firma) {
        return propuestaService.agregarFirma(propuestaId, firma);
    }
}
