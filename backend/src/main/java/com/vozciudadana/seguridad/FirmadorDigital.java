package com.vozciudadana.seguridad;

import com.vozciudadana.model.FirmaDigital;
import com.vozciudadana.model.Propuesta;

public interface FirmadorDigital {
    FirmaDigital firmar(Propuesta propuesta, FirmaDigital firmaDigital);
}
