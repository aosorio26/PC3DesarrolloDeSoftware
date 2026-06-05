package com.vozciudadana.seguridad;

import com.vozciudadana.model.Propuesta;

public interface CongeladorCriptografico {
    String generarHash(Propuesta propuesta);
}
