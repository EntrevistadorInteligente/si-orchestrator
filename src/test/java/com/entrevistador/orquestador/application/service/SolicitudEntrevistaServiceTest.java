package com.entrevistador.orquestador.application.service;

import com.entrevistador.orquestador.dominio.model.dto.FormularioDto;
import com.entrevistador.orquestador.dominio.model.dto.ProcesoEntrevistaDto;
import com.entrevistador.orquestador.dominio.model.dto.RagsIdsDto;
import com.entrevistador.orquestador.dominio.port.EntrevistaDao;
import com.entrevistador.orquestador.dominio.port.HojaDeVidaDao;
import com.entrevistador.orquestador.dominio.port.ProcesoEntrevistaDao;
import com.entrevistador.orquestador.dominio.port.jms.JmsPublisherClient;
import com.entrevistador.orquestador.dominio.service.ValidadacionEntrevistaPermitidaService;
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
    private ProcesoEntrevistaDao procesoEntrevistaDao;
    @Mock
    private ValidadacionEntrevistaPermitidaService validadacionEntrevistaPermitidaService;
    @Mock
    private EntrevistaDao entrevistaDao;
    @Mock
    private  HojaDeVidaDao hojaDeVidaDao;

    @Test
    void generarSolicitudEntrevistaTest() {
        String result = "result";
        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        Map<String, String> map = Map.of(
                "idHojaDeVidaRag", "theTitle",
                "idInformacionEmpresaRag", "theUrl",
                "hojaDeVidaValida", "true"
        );
        RagsIdsDto projection = factory.createProjection(RagsIdsDto.class, map);

        when(this.procesoEntrevistaDao.crearEvento()).thenReturn(Mono.just(ProcesoEntrevistaDto.builder().uuid("any").build()));
        when(this.entrevistaDao.crearEntrevistaBase(anyString(),any(),any())).thenReturn(Mono.just("projection"));
        when(this.jmsPublisherClient.enviarInformacionEmpresa(any())).thenReturn(Mono.empty());
        when(this.jmsPublisherClient.validarmatchHojaDeVida(any())).thenReturn(Mono.empty());
        when(this.hojaDeVidaDao.obtenerIdHojaDeVidaRag(anyString())).thenReturn(Mono.just("1"));
        when(this.validadacionEntrevistaPermitidaService.ejecutar(anyString())).thenReturn(Mono.empty());

        Mono<Void> publisher =this.solicitudEntrevistaService.generarSolicitudEntrevista("1", new FormularioDto());

        StepVerifier
                .create(publisher)
                .verifyComplete();

        verify(this.procesoEntrevistaDao, times(1)).crearEvento();
        verify(this.jmsPublisherClient, times(1)).enviarInformacionEmpresa(any());
        verify(this.jmsPublisherClient, times(1)).validarmatchHojaDeVida(any());
        verify(this.hojaDeVidaDao, times(1)).obtenerIdHojaDeVidaRag(any());
    }

}