package com.entrevistador.orquestador.dominio.service;

import com.entrevistador.orquestador.dominio.excepciones.EntrevistaProcesoException;
import com.entrevistador.orquestador.dominio.excepciones.LimiteEntrevistaException;
import com.entrevistador.orquestador.dominio.model.Entrevista;
import com.entrevistador.orquestador.dominio.model.EstadoEntrevista;
import com.entrevistador.orquestador.dominio.port.EntrevistaDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.time.Month;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidadacionEntrevistaPermitidaServiceTest {
    @InjectMocks
    private ValidadacionEntrevistaPermitidaService validadacionEntrevistaPermitidaService;
    @Mock
    private EntrevistaDao entrevistaDao;

    @Test
    void testEjecutar_SizeLessThanThree() {
        Entrevista entrevista = Entrevista.builder().fechaCreacion(LocalDateTime.now()).build();

        when(this.entrevistaDao.obtenerEstadoEntrevistaPorUsuario(anyString()))
                .thenReturn(Mono.just(EstadoEntrevista.builder().idEntrevista("any").estadoEntrevista("FN").build()));
        when(this.entrevistaDao.consultarUltimasEntrevistas(anyString())).thenReturn(Flux.just(entrevista));

        Mono<Void> publisher = this.validadacionEntrevistaPermitidaService.ejecutar("any");

        StepVerifier
                .create(publisher)
                .verifyComplete();

        verify(this.entrevistaDao, Mockito.times(1)).obtenerEstadoEntrevistaPorUsuario(anyString());
        verify(this.entrevistaDao, Mockito.times(1)).consultarUltimasEntrevistas(anyString());
    }

    @Test
    void testEjecutar_Duration() {
        Entrevista entrevista = Entrevista.builder()
                .fechaCreacion(LocalDateTime.of(2024, Month.JULY, 30, 0, 0))
                .build();

        when(this.entrevistaDao.obtenerEstadoEntrevistaPorUsuario(anyString()))
                .thenReturn(Mono.just(EstadoEntrevista.builder().idEntrevista("any").estadoEntrevista("FN").build()));
        when(this.entrevistaDao.consultarUltimasEntrevistas(anyString())).thenReturn(Flux.just(entrevista, entrevista,
                entrevista, entrevista));

        Mono<Void> publisher = this.validadacionEntrevistaPermitidaService.ejecutar("any");

        StepVerifier
                .create(publisher)
                .verifyComplete();

        verify(this.entrevistaDao, Mockito.times(1)).obtenerEstadoEntrevistaPorUsuario(anyString());
        verify(this.entrevistaDao, Mockito.times(1)).consultarUltimasEntrevistas(anyString());
    }

    @Test
    void testEjecutar_EntrevistaProcesoException() {
        Entrevista entrevista = Entrevista.builder().fechaCreacion(LocalDateTime.now()).build();

        when(this.entrevistaDao.obtenerEstadoEntrevistaPorUsuario(anyString()))
                .thenReturn(Mono.just(EstadoEntrevista.builder().idEntrevista("any").estadoEntrevista("FG").build()));
        when(this.entrevistaDao.consultarUltimasEntrevistas(anyString())).thenReturn(Flux.just(entrevista));

        Mono<Void> publisher = this.validadacionEntrevistaPermitidaService.ejecutar("any");

        StepVerifier
                .create(publisher)
                .expectError(EntrevistaProcesoException.class)
                .verify();

        verify(this.entrevistaDao, Mockito.times(1)).obtenerEstadoEntrevistaPorUsuario(anyString());
        verify(this.entrevistaDao, Mockito.times(1)).consultarUltimasEntrevistas(anyString());
    }

    @Test
    void testEjecutar_LimiteEntrevistaException() {
        Entrevista entrevista = Entrevista.builder()
                .fechaCreacion(LocalDateTime.now())
                .build();

        when(this.entrevistaDao.obtenerEstadoEntrevistaPorUsuario(anyString()))
                .thenReturn(Mono.just(EstadoEntrevista.builder().idEntrevista("any").estadoEntrevista("FN").build()));
        when(this.entrevistaDao.consultarUltimasEntrevistas(anyString())).thenReturn(Flux.just(entrevista, entrevista,
                entrevista, entrevista));

        Mono<Void> publisher = this.validadacionEntrevistaPermitidaService.ejecutar("any");

        StepVerifier
                .create(publisher)
                .expectError(LimiteEntrevistaException.class)
                .verify();

        verify(this.entrevistaDao, Mockito.times(1)).obtenerEstadoEntrevistaPorUsuario(anyString());
        verify(this.entrevistaDao, Mockito.times(1)).consultarUltimasEntrevistas(anyString());
    }
}