package com.entrevistador.orquestador.application.service;

import com.entrevistador.orquestador.dominio.model.dto.MensajeValidacionMatch;
import com.entrevistador.orquestador.dominio.model.dto.RagsIdsDto;
import com.entrevistador.orquestador.dominio.model.dto.SolicitudGeneracionEntrevistaDto;
import com.entrevistador.orquestador.dominio.port.EntrevistaDao;
import com.entrevistador.orquestador.dominio.port.jms.JmsPublisherClient;
import com.entrevistador.orquestador.dominio.port.sse.SseService;
import com.entrevistador.orquestador.dominio.service.ValidadorEventosSimultaneosService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Collections;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrquestadorEntrevistaServiceTest {
    @InjectMocks
    private OrquestadorEntrevistaService orquestadorEntrevistaService;
    @Mock
    private JmsPublisherClient jmsPublisherClient;
    @Mock
    private SseService sseService;
    @Mock
    private EntrevistaDao entrevistaDao;
    @Mock
    private ValidadorEventosSimultaneosService validadorEventosSimultaneosService;

    private RagsIdsDto projection;

    @BeforeEach
    void setUp() {
        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        Map<String, String> map = Map.of(
                "idHojaDeVidaRag", "theTitle",
                "idInformacionEmpresaRag", "theUrl"
        );

        this.projection = factory.createProjection(RagsIdsDto.class, map);
    }

    @Test
    @DisplayName("Test para Receptor Información Empresa")
    void testReceptorInformacionEmpresa() {
        when(this.entrevistaDao.actualizarIdInformacionEmpresaRag(anyString(), anyString())).thenReturn(Mono.empty());
        when(this.validadorEventosSimultaneosService.ejecutar(anyString())).thenReturn(Mono.just(this.projection));
        when(this.jmsPublisherClient.generarEntrevista(any(SolicitudGeneracionEntrevistaDto.class)))
                .thenReturn(Mono.empty());

        Mono<Void> publisher = this.orquestadorEntrevistaService.receptorInformacionEmpresa("any", "any");

        StepVerifier
                .create(publisher)
                .verifyComplete();

        verify(this.entrevistaDao, times(1)).actualizarIdInformacionEmpresaRag(anyString(), anyString());
        verify(this.validadorEventosSimultaneosService, times(1)).ejecutar(anyString());
        verify(this.jmsPublisherClient, times(1)).generarEntrevista(any(SolicitudGeneracionEntrevistaDto.class));
    }

    @Test
    @DisplayName("Test para Enviar Preguntas Front SSE")
    void testEnviarPreguntasFront() {
        when(this.sseService.emitEvent(any())).thenReturn(Mono.empty());

        Mono<Void> publisher = this.orquestadorEntrevistaService.enviarPreguntasFront(Collections.emptyList());

        StepVerifier
                .create(publisher)
                .verifyComplete();

        verify(this.sseService, times(1)).emitEvent(any());
    }

    @Test
    @DisplayName("Test para Receptor Hoja De Vida Match")
    void testReceptorHojaDeVidaMatch() {
        when(this.entrevistaDao.actualizarEstadoEntrevista(anyString(), anyBoolean())).thenReturn(Mono.empty());
        when(this.validadorEventosSimultaneosService.ejecutar(anyString())).thenReturn(Mono.just(this.projection));
        when(this.jmsPublisherClient.generarEntrevista(any(SolicitudGeneracionEntrevistaDto.class)))
                .thenReturn(Mono.empty());

        MensajeValidacionMatch mensajeValidacion = MensajeValidacionMatch.builder().idEntrevista("any").matchValido(Boolean.TRUE).build();
        Mono<Void> publisher = this.orquestadorEntrevistaService.receptorHojaDeVidaMatch(mensajeValidacion);

        StepVerifier
                .create(publisher)
                .verifyComplete();

        verify(this.entrevistaDao, times(1)).actualizarEstadoEntrevista(anyString(), anyBoolean());
        verify(this.validadorEventosSimultaneosService, times(1)).ejecutar(anyString());
        verify(this.jmsPublisherClient, times(1)).generarEntrevista(any(SolicitudGeneracionEntrevistaDto.class));
    }
}