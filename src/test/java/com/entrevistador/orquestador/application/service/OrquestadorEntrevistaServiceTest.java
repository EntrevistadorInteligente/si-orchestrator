package com.entrevistador.orquestador.application.service;

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

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
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
}