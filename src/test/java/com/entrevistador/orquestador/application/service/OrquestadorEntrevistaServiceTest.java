package com.entrevistador.orquestador.application.service;

import com.entrevistador.orquestador.dominio.model.EntrevistaModel;
import com.entrevistador.orquestador.dominio.model.Feedback;
import com.entrevistador.orquestador.dominio.model.FeedbackComentario;
import com.entrevistador.orquestador.dominio.model.MensajeValidacionMatch;
import com.entrevistador.orquestador.dominio.model.PreguntaGenerada;
import com.entrevistador.orquestador.dominio.model.SolicitudGeneracionEntrevista;
import com.entrevistador.orquestador.dominio.port.EntrevistaDao;
import com.entrevistador.orquestador.dominio.port.jms.JmsPublisherClient;
import com.entrevistador.orquestador.dominio.service.ValidadorEventosSimultaneosService;
import com.entrevistador.orquestador.infrastructure.adapter.dto.RagsIdsDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;
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
    private EntrevistaDao entrevistaDao;
    @Mock
    private ValidadorEventosSimultaneosService validadorEventosSimultaneosService;

    @Test
    void testReceptorInformacionEmpresa_WhenSolicitudEventosSimultaneosServiceRetornaTrue() {
        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        Map<String, String> map = Map.of(
                "idHojaDeVidaRag", "theTitle",
                "idInformacionEmpresaRag", "theUrl"
        );
        RagsIdsDto projection = factory.createProjection(RagsIdsDto.class, map);

        when(this.validadorEventosSimultaneosService.ejecutar(anyString())).thenReturn(Mono.just(projection));
        when(this.entrevistaDao.actualizarIdInformacionEmpresaRag(anyString(), any())).thenReturn(Mono.empty());
        when(this.jmsPublisherClient.generarEntrevista(any(SolicitudGeneracionEntrevista.class)))
                .thenReturn(Mono.empty());

        Mono<Void> publisher = this.orquestadorEntrevistaService.receptorInformacionEmpresa("any", "any");

        StepVerifier
                .create(publisher)
                .verifyComplete();

        verify(this.validadorEventosSimultaneosService, times(1)).ejecutar(anyString());
        verify(this.jmsPublisherClient, times(1)).generarEntrevista(any(SolicitudGeneracionEntrevista.class));
    }

    @Test
    void testReceptorInformacionEmpresa_WhenSolicitudEventosSimultaneosServiceRetornaFalse() {
        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        Map<String, String> map = Map.of(
                "idHojaDeVidaRag", "theTitle",
                "idInformacionEmpresaRag", "theUrl"
        );
        RagsIdsDto projection = factory.createProjection(RagsIdsDto.class, map);

        when(this.validadorEventosSimultaneosService.ejecutar(anyString())).thenReturn(Mono.just(projection));
        when(this.entrevistaDao.actualizarIdInformacionEmpresaRag(anyString(), any())).thenReturn(Mono.empty());
        when(this.jmsPublisherClient.generarEntrevista(any(SolicitudGeneracionEntrevista.class)))
                .thenReturn(Mono.empty());

        Mono<Void> publisher = this.orquestadorEntrevistaService.receptorInformacionEmpresa("any", "any");

        StepVerifier
                .create(publisher)
                .verifyComplete();

        verify(this.validadorEventosSimultaneosService, times(1)).ejecutar(anyString());
    }

    @Test
    void testReceptorHojaDeVidaMatch() {
        MensajeValidacionMatch mensajeValidacionMatch = MensajeValidacionMatch.builder().idEntrevista("any").build();
        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        Map<String, String> map = Map.of(
                "idHojaDeVidaRag", "theTitle",
                "idInformacionEmpresaRag", "theUrl"
        );
        RagsIdsDto projection = factory.createProjection(RagsIdsDto.class, map);

        when(this.entrevistaDao.actualizarEstadoHojaDeVida(anyString(), anyBoolean())).thenReturn(Mono.empty());
        when(this.validadorEventosSimultaneosService.ejecutar(anyString())).thenReturn(Mono.just(projection));
        when(this.jmsPublisherClient.generarEntrevista(any()))
                .thenReturn(Mono.empty());

        Mono<Void> publisher = this.orquestadorEntrevistaService.receptorHojaDeVidaMatch(mensajeValidacionMatch);

        StepVerifier
                .create(publisher)
                .verifyComplete();

        verify(this.entrevistaDao, times(1)).actualizarEstadoHojaDeVida(anyString(), anyBoolean());
        verify(this.validadorEventosSimultaneosService, times(1)).ejecutar(anyString());
        verify(this.jmsPublisherClient, times(1)).generarEntrevista(any());
    }
    @Test
    void testReceptorHojaDeVidaMatch_RagsIdsDtoNull() {
        MensajeValidacionMatch mensajeValidacionMatch = MensajeValidacionMatch.builder().idEntrevista("any").build();

        when(this.entrevistaDao.actualizarEstadoHojaDeVida(anyString(), anyBoolean())).thenReturn(Mono.empty());
        when(this.validadorEventosSimultaneosService.ejecutar(anyString())).thenReturn(Mono.empty());

        Mono<Void> publisher = this.orquestadorEntrevistaService.receptorHojaDeVidaMatch(mensajeValidacionMatch);

        StepVerifier
                .create(publisher)
                .verifyComplete();

        verify(this.entrevistaDao, times(1)).actualizarEstadoHojaDeVida(anyString(), anyBoolean());
        verify(this.validadorEventosSimultaneosService, times(1)).ejecutar(anyString());
        verify(this.jmsPublisherClient, times(0)).generarEntrevista(any());
    }

    @Test
    void testActualizarEstadoEntrevistaPorPreguntas() {
        PreguntaGenerada preguntaGenerada = PreguntaGenerada.builder().build();
        EntrevistaModel entrevistaModel = EntrevistaModel.builder().preguntas(List.of(preguntaGenerada)).idEntrevista("any").build();

        when(this.entrevistaDao.actualizarEstadoEntrevista(anyString(), any()))
                .thenReturn(Mono.empty());

        Mono<Void> publisher = this.orquestadorEntrevistaService.actualizarEstadoEntrevistaPorPreguntas(entrevistaModel);

        StepVerifier
                .create(publisher)
                .verifyComplete();

        verify(this.entrevistaDao, times(1)).actualizarEstadoEntrevista(anyString(), any());
    }

    @Test
    void testActualizarEstadoEntrevistaPorPreguntas_Empty() {
        EntrevistaModel entrevistaModel = EntrevistaModel.builder().preguntas(List.of()).idEntrevista("any").build();

        Mono<Void> publisher = this.orquestadorEntrevistaService.actualizarEstadoEntrevistaPorPreguntas(entrevistaModel);

        StepVerifier
                .create(publisher)
                .verifyComplete();

        verify(this.entrevistaDao, times(0)).actualizarEstadoEntrevista(anyString(), any());
    }

    @Test
    void testActualizarEstadoEntrevistaPorFeedback() {
        FeedbackComentario feedbackComentario = FeedbackComentario.builder().build();
        Feedback feedback = Feedback.builder().procesoEntrevista(List.of(feedbackComentario)).idEntrevista("any").build();

        when(this.entrevistaDao.actualizarEstadoEntrevista(anyString(), any()))
                .thenReturn(Mono.empty());

        Mono<Void> publisher = this.orquestadorEntrevistaService.actualizarEstadoEntrevistaPorFeedback(feedback);

        StepVerifier
                .create(publisher)
                .verifyComplete();

        verify(this.entrevistaDao, times(1)).actualizarEstadoEntrevista(anyString(), any());
    }

    @Test
    void testActualizarEstadoEntrevistaPorFeedback_Empty() {
        Feedback feedback = Feedback.builder().procesoEntrevista(List.of()).idEntrevista("any").build();

        Mono<Void> publisher = this.orquestadorEntrevistaService.actualizarEstadoEntrevistaPorFeedback(feedback);

        StepVerifier
                .create(publisher)
                .verifyComplete();

        verify(this.entrevistaDao, times(0)).actualizarEstadoEntrevista(anyString(), any());
    }
}