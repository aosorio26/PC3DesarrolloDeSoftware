package com.vozciudadana;

import com.vozciudadana.seguridad.ServicioHashSha256;
import com.vozciudadana.seguridad.FirmadorDigitalSimulado;
import com.vozciudadana.model.Firma;
import com.vozciudadana.model.Propuesta;
import com.vozciudadana.repository.PropuestaRepository;
import com.vozciudadana.service.PropuestaService;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PropuestaServiceTest {
    @Test
    void creaPropuestaAbiertaConFechaLimite() {
        PropuestaRepository repository = mock(PropuestaRepository.class);
        PropuestaService service = new PropuestaService(repository, new ServicioHashSha256(), new FirmadorDigitalSimulado());
        Propuesta propuesta = new Propuesta();
        propuesta.setTitulo("Ley de reciclaje barrial");
        propuesta.setColectivo("Vecinos Unidos");
        propuesta.setResumen("Promueve reciclaje municipal");
        propuesta.setTextoNormativo("Artículo 1. Créase el programa.");
        when(repository.save(any(Propuesta.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Propuesta guardada = service.crear(propuesta);

        assertEquals("ABIERTA", guardada.getEstado().name());
        assertEquals(90, java.time.Duration.between(guardada.getFechaCreacion(), guardada.getFechaLimite()).toDays());
    }

    @Test
    void rechazaPropuestaVencida() {
        PropuestaRepository repository = mock(PropuestaRepository.class);
        PropuestaService service = new PropuestaService(repository, new ServicioHashSha256(), new FirmadorDigitalSimulado());
        Propuesta propuesta = new Propuesta();
        propuesta.setFechaLimite(LocalDateTime.now().minusDays(1));

        assertThrows(IllegalStateException.class, () -> service.validarAbierta(propuesta));
    }

    @Test
    void agregaFirmaValida() {
        PropuestaRepository repository = mock(PropuestaRepository.class);
        PropuestaService service = new PropuestaService(repository, new ServicioHashSha256(), new FirmadorDigitalSimulado());
        Propuesta propuesta = new Propuesta();
        propuesta.setId("abc123");
        when(repository.findById("abc123")).thenReturn(Optional.of(propuesta));
        when(repository.save(any(Propuesta.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Propuesta actualizada = service.agregarFirma("abc123", new Firma("12345678", "Ana Torres", "ana@mail.com"));

        assertEquals(1, actualizada.cantidadFirmasValidas());
    }
}
