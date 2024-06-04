package com.entrevistador.orquestador.dominio.service;

import com.entrevistador.orquestador.dominio.excepciones.ActualizarEstadoEntrevistaException;
import com.entrevistador.orquestador.dominio.excepciones.IdNoEncontradoException;
import com.entrevistador.orquestador.dominio.model.ProcesoEntrevista;
import com.entrevistador.orquestador.dominio.model.enums.EstadoProcesoEnum;
import com.entrevistador.orquestador.dominio.model.enums.FuenteEnum;
import com.entrevistador.orquestador.dominio.port.ProcesoEntrevistaDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ActualizarEstadoProcesoEntrevistaServiceTest {
    @InjectMocks
    private ActualizarEstadoProcesoEntrevistaService actualizarEstadoProcesoEntrevistaService;
    @Mock
    private ProcesoEntrevistaDao procesoEntrevistaDao;

    private ProcesoEntrevista procesoEntrevistaDtoVacio = ProcesoEntrevista.builder()
            .uuid("")
            .fechaYHora(null)
            .estado(null)
            .fuente(null)
            .error("")
            .build();

    private ProcesoEntrevista procesoEntrevistaDtoActualizar = ProcesoEntrevista.builder()
            .uuid("any")
            .fechaYHora(new Date())
            .estado(EstadoProcesoEnum.AC)
            .fuente(FuenteEnum.ANALIZADOR)
            .error("")
            .build();

    @Test
    void dtoVacioActualizarTest() {
        assertThrows(ActualizarEstadoEntrevistaException.class, () ->
                this.procesoEntrevistaDtoActualizar.actualizar(procesoEntrevistaDtoVacio));
    }

    @Test
    void dtoActualizarTest() {
        assertTrue(this.procesoEntrevistaDtoVacio.actualizar(procesoEntrevistaDtoActualizar));
    }

    @Test
    void testEjecutar() {
        when(this.procesoEntrevistaDao.obtenerEventoPorId(anyString())).thenReturn(Mono.just(procesoEntrevistaDtoVacio));
        when(this.procesoEntrevistaDao.actualizar(any())).thenReturn(Mono.just(procesoEntrevistaDtoActualizar));

        Mono<Void> publisher = this.actualizarEstadoProcesoEntrevistaService.ejecutar(procesoEntrevistaDtoActualizar);

        StepVerifier
                .create(publisher)
                .verifyComplete();

        verify(this.procesoEntrevistaDao, timeout(1)).obtenerEventoPorId(anyString());
        verify(this.procesoEntrevistaDao, times(1)).actualizar(any());
    }

    @Test
    void testEjecutar_WhenIdNoEncontradoException() {
        when(this.procesoEntrevistaDao.obtenerEventoPorId(anyString())).thenReturn(Mono.empty());

        Mono<Void> publisher = this.actualizarEstadoProcesoEntrevistaService.ejecutar(procesoEntrevistaDtoVacio);

        StepVerifier
                .create(publisher)
                .expectError(IdNoEncontradoException.class)
                .verify();

        verify(this.procesoEntrevistaDao, timeout(1)).obtenerEventoPorId(anyString());
        verify(this.procesoEntrevistaDao, times(0)).actualizar(any());
    }

    @Test
    void testEjecutar_WhenActualizarEstadoEntrevistaException() {
        when(this.procesoEntrevistaDao.obtenerEventoPorId(anyString())).thenReturn(Mono.just(procesoEntrevistaDtoActualizar));

        Mono<Void> publisher = this.actualizarEstadoProcesoEntrevistaService.ejecutar(procesoEntrevistaDtoVacio);

        StepVerifier
                .create(publisher)
                .expectError(ActualizarEstadoEntrevistaException.class)
                .verify();

        verify(this.procesoEntrevistaDao, timeout(1)).obtenerEventoPorId(anyString());
        verify(this.procesoEntrevistaDao, times(0)).actualizar(any());
    }
}