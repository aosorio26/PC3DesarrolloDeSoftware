package com.vozciudadana.recursos;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CatalogoTiposRecurso {
    private final Map<String, TipoRecursoCompartido> tipos = new HashMap<>();

    public CatalogoTiposRecurso() {
        registrar(new TipoRecursoCompartido("ESTUDIO", "Estudio", "EST", "Documento técnico que sustenta la propuesta"));
        registrar(new TipoRecursoCompartido("ESTADISTICA", "Estadística", "DAT", "Dato numérico o indicador relacionado"));
        registrar(new TipoRecursoCompartido("VIDEO", "Video", "VID", "Material audiovisual de explicación o evidencia"));
        registrar(new TipoRecursoCompartido("AUDIO", "Audio", "AUD", "Registro sonoro, entrevista o testimonio"));
        registrar(new TipoRecursoCompartido("DOCUMENTO", "Documento", "DOC", "Archivo general de respaldo"));
    }

    public TipoRecursoCompartido obtener(String codigo) {
        String clave = normalizar(codigo);
        if (tipos.containsKey(clave)) {
            return tipos.get(clave);
        }
        return tipos.get("DOCUMENTO");
    }

    public Map<String, TipoRecursoCompartido> listar() {
        return tipos;
    }

    private void registrar(TipoRecursoCompartido tipo) {
        tipos.put(tipo.getCodigo(), tipo);
    }

    private String normalizar(String texto) {
        if (texto == null || texto.isBlank()) {
            return "DOCUMENTO";
        }
        return texto.trim().toUpperCase()
                .replace("Í", "I")
                .replace("Á", "A")
                .replace("É", "E")
                .replace("Ó", "O")
                .replace("Ú", "U")
                .replace(" ", "_");
    }
}
