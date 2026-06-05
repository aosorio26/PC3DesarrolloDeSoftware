package com.vozciudadana.seguridad;

import com.vozciudadana.model.FirmaDigital;
import com.vozciudadana.model.Propuesta;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.HexFormat;

@Component
public class FirmadorDigitalSimulado implements FirmadorDigital {
    public FirmaDigital firmar(Propuesta propuesta, FirmaDigital firmaDigital) {
        firmaDigital.setAlgoritmo("SHA256withRSA");
        firmaDigital.setValorFirma(generarValorSimulado(propuesta, firmaDigital));
        firmaDigital.setEstadoValidacion("PENDIENTE_VALIDACION");
        firmaDigital.setFechaRegistro(LocalDateTime.now());
        return firmaDigital;
    }

    private String generarValorSimulado(Propuesta propuesta, FirmaDigital firmaDigital) {
        try {
            String base = propuesta.getId() + "|" + propuesta.getTitulo() + "|" + firmaDigital.getFirmante() + "|" + firmaDigital.getCertificadoReferencia();
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes(StandardCharsets.UTF_8));
            return "SIM-" + HexFormat.of().formatHex(hash);
        } catch (Exception e) {
            throw new IllegalStateException("No se pudo generar la firma digital simulada");
        }
    }
}
