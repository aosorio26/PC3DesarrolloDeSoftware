package com.vozciudadana.resumen;

import com.vozciudadana.model.Propuesta;

public class ResumenConFirmas implements ResumenPropuesta {
    private final ResumenPropuesta resumen;
    private final Propuesta propuesta;

    public ResumenConFirmas(ResumenPropuesta resumen, Propuesta propuesta) {
        this.resumen = resumen;
        this.propuesta = propuesta;
    }

    @Override
    public String generar() {
        return resumen.generar() + " | Firmas válidas: " + propuesta.cantidadFirmasValidas();
    }
}
