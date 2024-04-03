package com.entrevistador.orquestador.application.service;

import com.entrevistador.orquestador.dominio.model.dto.FormularioDto;
import com.entrevistador.orquestador.dominio.model.dto.HojaDeVidaDto;
import com.entrevistador.orquestador.dominio.port.client.PreparadorClient;
import com.entrevistador.orquestador.dominio.port.sse.SseService;
import com.entrevistador.orquestador.dominio.service.ActualizarInformacionEntrevistaService;
import com.entrevistador.orquestador.dominio.service.SolicitudPreparacionEntrevistaService;
import com.entrevistador.orquestador.dominio.service.ValidadorEventosSimultaneosService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrquestadorEntrevistaServiceTest {
    @InjectMocks
    private OrquestadorEntrevistaService orquestadorEntrevistaService;
    @Mock
    private SolicitudPreparacionEntrevistaService solicitudPreparacionEntrevistaService;
    @Mock
    private ActualizarInformacionEntrevistaService actualizarInformacionEntrevistaService;
    @Mock
    private ValidadorEventosSimultaneosService validadorEventosSimultaneosService;
    @Mock
    private PreparadorClient preparadorClient;
    @Mock
    private SseService sseService;

    @Test
    void testReceptorHojaDeVida_WhenSolicitudEventosSimultaneosServiceRetornaTrue() {
        when(this.actualizarInformacionEntrevistaService.actualizarHojaDeVida(anyString(), any())).thenReturn(Mono.just("any"));
        when(this.validadorEventosSimultaneosService.ejecutar(anyString())).thenReturn(Mono.just(Boolean.TRUE));
        when(this.solicitudPreparacionEntrevistaService.ejecutar()).thenReturn("any");
        when(this.preparadorClient.generarEntrevista()).thenReturn(Mono.empty());

        Mono<Void> publisher = this.orquestadorEntrevistaService.receptorHojaDeVida("any", HojaDeVidaDto.builder().build());

        StepVerifier
                .create(publisher)
                .verifyComplete();

        verify(this.actualizarInformacionEntrevistaService, times(1)).actualizarHojaDeVida(anyString(), any());
        verify(this.validadorEventosSimultaneosService, timeout(1)).ejecutar(anyString());
        verify(this.sseService, times(1)).emitEvent(any());
        verify(this.solicitudPreparacionEntrevistaService, times(1)).ejecutar();
        verify(this.preparadorClient, times(1)).generarEntrevista();
    }

    @Test
    void testReceptorHojaDeVida_WhenSolicitudEventosSimultaneosServiceRetornaFalse() {
        when(this.actualizarInformacionEntrevistaService.actualizarHojaDeVida(anyString(), any())).thenReturn(Mono.just("any"));
        when(this.validadorEventosSimultaneosService.ejecutar(anyString())).thenReturn(Mono.just(Boolean.FALSE));

        Mono<Void> publisher = this.orquestadorEntrevistaService.receptorHojaDeVida("any", HojaDeVidaDto.builder().build());

        StepVerifier
                .create(publisher)
                .verifyComplete();

        verify(this.actualizarInformacionEntrevistaService, times(1)).actualizarHojaDeVida(anyString(), any());
        verify(this.validadorEventosSimultaneosService, timeout(1)).ejecutar(anyString());
        verify(this.sseService, times(0)).emitEvent(any());
        verify(this.solicitudPreparacionEntrevistaService, times(0)).ejecutar();
        verify(this.preparadorClient, times(0)).generarEntrevista();
    }

    @Test
    void testReceptorInformacionEmpresa_WhenSolicitudEventosSimultaneosServiceRetornaTrue() {
        when(this.actualizarInformacionEntrevistaService.actualizarInformacionEmpresa(anyString(), any(), anyList())).thenReturn(Mono.just("any"));
        when(this.validadorEventosSimultaneosService.ejecutar(anyString())).thenReturn(Mono.just(Boolean.TRUE));
        when(this.solicitudPreparacionEntrevistaService.ejecutar()).thenReturn("any");
        when(this.preparadorClient.generarEntrevista()).thenReturn(Mono.empty());

        Mono<Void> publisher = this.orquestadorEntrevistaService.receptorInformacionEmpresa("any", FormularioDto.builder().build(), List.of("any"));

        StepVerifier
                .create(publisher)
                .verifyComplete();

        verify(this.actualizarInformacionEntrevistaService, times(1)).actualizarInformacionEmpresa(anyString(), any(), anyList());
        verify(this.validadorEventosSimultaneosService, timeout(1)).ejecutar(anyString());
        verify(this.sseService, times(1)).emitEvent(any());
        verify(this.solicitudPreparacionEntrevistaService, times(1)).ejecutar();
        verify(this.preparadorClient, times(1)).generarEntrevista();
    }

    @Test
    void testReceptorInformacionEmpresa_WhenSolicitudEventosSimultaneosServiceRetornaFalse() {
        when(this.actualizarInformacionEntrevistaService.actualizarInformacionEmpresa(anyString(), any(), anyList())).thenReturn(Mono.just("any"));
        when(this.validadorEventosSimultaneosService.ejecutar(anyString())).thenReturn(Mono.just(Boolean.FALSE));

        Mono<Void> publisher = this.orquestadorEntrevistaService.receptorInformacionEmpresa("any", FormularioDto.builder().build(), List.of("any"));

        StepVerifier
                .create(publisher)
                .verifyComplete();

        verify(this.actualizarInformacionEntrevistaService, times(1)).actualizarInformacionEmpresa(anyString(), any(), anyList());
        verify(this.validadorEventosSimultaneosService, timeout(1)).ejecutar(anyString());
        verify(this.sseService, times(0)).emitEvent(any());
        verify(this.solicitudPreparacionEntrevistaService, times(0)).ejecutar();
        verify(this.preparadorClient, times(0)).generarEntrevista();
    }
}