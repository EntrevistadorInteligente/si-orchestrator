package com.entrevistador.orquestador.application.service;

import com.entrevistador.orquestador.dominio.model.dto.FormularioDto;
import com.entrevistador.orquestador.dominio.model.dto.ProcesoEntrevistaDto;
import com.entrevistador.orquestador.dominio.model.dto.RagsIdsDto;
import com.entrevistador.orquestador.dominio.port.EntrevistaDao;
import com.entrevistador.orquestador.dominio.port.ProcesoEntrevistaDao;
import com.entrevistador.orquestador.dominio.port.jms.JmsPublisherClient;
import com.entrevistador.orquestador.dominio.service.ValidadorPdfService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


import java.util.Map;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SolicitudEntrevistaServiceTest {

    @InjectMocks
    private SolicitudEntrevistaService solicitudEntrevistaService;
    @Mock
    private JmsPublisherClient jmsPublisherClient;
    @Mock
    private ValidadorPdfService validadorPdfService;
    @Mock
    private ProcesoEntrevistaDao procesoEntrevistaDao;
    @Mock
    private EntrevistaDao entrevistaDao;

    @Test
    void generarSolicitudEntrevistaTest() {
        String result = "result";
        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        Map<String, String> map = Map.of(
                "idHojaDeVidaRag", "theTitle",
                "idInformacionEmpresaRag", "theUrl"
        );
        RagsIdsDto projection = factory.createProjection(RagsIdsDto.class, map);

        when(this.validadorPdfService.ejecutar(any())).thenReturn(Mono.just(new byte[]{}));
        when(this.entrevistaDao.consultarRagsId(any())).thenReturn(Mono.just(projection));
        when(this.procesoEntrevistaDao.crearEvento()).thenReturn(Mono.just(ProcesoEntrevistaDto.builder().uuid("any").build()));
        when(this.jmsPublisherClient.enviarInformacionEmpresa(any())).thenReturn(Mono.empty());

        Mono<Void> publisher = this.solicitudEntrevistaService.generarSolicitudEntrevista("", new FormularioDto());

        StepVerifier
                .create(publisher)
                .verifyComplete();

        verify(this.validadorPdfService, times(1)).ejecutar(any());
        verify(this.procesoEntrevistaDao, times(1)).crearEvento();
        verify(this.jmsPublisherClient, times(1)).enviarInformacionEmpresa(any());
    }

}