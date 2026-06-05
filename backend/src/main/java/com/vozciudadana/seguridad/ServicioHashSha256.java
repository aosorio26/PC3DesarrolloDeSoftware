package com.vozciudadana.seguridad;

import com.vozciudadana.model.Firma;
import com.vozciudadana.model.Propuesta;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@Component
public class ServicioHashSha256 implements CongeladorCriptografico {
    @Override
    public String generarHash(Propuesta propuesta) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(contenido(propuesta).getBytes(StandardCharsets.UTF_8));
            StringBuilder resultado = new StringBuilder();
            for (byte item : hash) {
                resultado.append(String.format("%02x", item));
            }
            return resultado.toString();
        } catch (Exception e) {
            throw new IllegalStateException("No se pudo congelar el archivo");
        }
    }

    private String contenido(Propuesta propuesta) {
        StringBuilder texto = new StringBuilder();
        texto.append(propuesta.getTitulo()).append("|");
        texto.append(propuesta.getColectivo()).append("|");
        texto.append(propuesta.getResumen()).append("|");
        texto.append(propuesta.getTextoNormativo()).append("|");
        for (Firma firma : propuesta.getFirmas()) {
            texto.append(firma.getDni()).append(":").append(firma.isValida()).append("|");
        }
        texto.append(propuesta.getComentarios().size()).append("|");
        texto.append(propuesta.getRecursos().size()).append("|");
        texto.append(propuesta.getModificaciones().size());
        return texto.toString();
    }
}
