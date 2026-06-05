package com.vozciudadana.resumen;

import com.vozciudadana.model.Propuesta;

public class ResumenConRecursos implements ResumenPropuesta {
    private final ResumenPropuesta resumen;
    private final Propuesta propuesta;

    public ResumenConRecursos(ResumenPropuesta resumen, Propuesta propuesta) {
        this.resumen = resumen;
        this.propuesta = propuesta;
    }

    @Override
    public String generar() {
        return resumen.generar() + " | Recursos: " + propuesta.getRecursos().size();
    }
}
