package com.vozciudadana;

import com.vozciudadana.seguridad.ServicioHashSha256;
import com.vozciudadana.resumen.ResumenBase;
import com.vozciudadana.resumen.ResumenConFirmas;
import com.vozciudadana.resumen.ResumenConRecursos;
import com.vozciudadana.resumen.ResumenPropuesta;
import com.vozciudadana.model.Firma;
import com.vozciudadana.model.Propuesta;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PatronesTest {
    @Test
    void hashGeneraSha256() {
        Propuesta propuesta = new Propuesta();
        propuesta.setTitulo("Ley de parques");
        propuesta.setColectivo("Colectivo Verde");
        propuesta.setResumen("Protección de parques");
        propuesta.setTextoNormativo("Artículo 1");

        String hash = new ServicioHashSha256().generarHash(propuesta);

        assertEquals(64, hash.length());
    }

    @Test
    void resumenAgregaDatos() {
        Propuesta propuesta = new Propuesta();
        propuesta.setTitulo("Ley de agua");
        propuesta.setResumen("Acceso al agua");
        propuesta.getFirmas().add(new Firma("12345678", "Luis Paz", "luis@mail.com"));
        ResumenPropuesta resumen = new ResumenConRecursos(new ResumenConFirmas(new ResumenBase(propuesta), propuesta), propuesta);

        String texto = resumen.generar();

        assertTrue(texto.contains("Firmas válidas: 1"));
        assertTrue(texto.contains("Recursos: 0"));
    }
}
