package com.vozciudadana.resumen;

import com.vozciudadana.model.Propuesta;

public class ResumenBase implements ResumenPropuesta {
    private final Propuesta propuesta;

    public ResumenBase(Propuesta propuesta) {
        this.propuesta = propuesta;
    }

    @Override
    public String generar() {
        return propuesta.getTitulo() + " - " + propuesta.getResumen();
    }
}
