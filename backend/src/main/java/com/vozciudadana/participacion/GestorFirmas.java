package com.vozciudadana.participacion;

import com.vozciudadana.model.Firma;
import com.vozciudadana.model.Propuesta;

public interface GestorFirmas {
    Propuesta firmar(String propuestaId, Firma firma);
}
