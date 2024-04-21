package com.entrevistador.orquestador.application.service;

import com.entrevistador.orquestador.dominio.model.dto.FormularioDto;
import com.entrevistador.orquestador.dominio.model.dto.ProcesoEntrevistaDto;
import com.entrevistador.orquestador.dominio.port.EntrevistaDao;
import com.entrevistador.orquestador.dominio.port.HojaDeVidaDao;
import com.entrevistador.orquestador.dominio.port.ProcesoEntrevistaDao;
import com.entrevistador.orquestador.dominio.port.jms.JmsPublisherClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SolicitudEntrevistaServiceTest {

    @InjectMocks
    private SolicitudEntrevistaService solicitudEntrevistaService;
    @Mock
    private JmsPublisherClient jmsPublisherClient;
    @Mock
    private ProcesoEntrevistaDao procesoEntrevistaDao;
    @Mock
    private EntrevistaDao entrevistaDao;
    @Mock
    private HojaDeVidaDao hojaDeVidaDao;

    @Test
    void generarSolicitudEntrevistaTest() {
        when(this.hojaDeVidaDao.obtenerIdHojaDeVidaRag(anyString())).thenReturn(Mono.just("any"));
        when(this.entrevistaDao.crearEntrevistaBase(anyString(), anyString(), any())).thenReturn(Mono.just("any"));
        when(this.procesoEntrevistaDao.crearEvento()).thenReturn(Mono.just(ProcesoEntrevistaDto.builder().uuid("any").build()));
        when(this.jmsPublisherClient.enviarInformacionEmpresa(any())).thenReturn(Mono.empty());

        Mono<Void> publisher = this.solicitudEntrevistaService.generarSolicitudEntrevista("any", new FormularioDto());

        StepVerifier
                .create(publisher)
                .verifyComplete();

        verify(this.hojaDeVidaDao, times(1)).obtenerIdHojaDeVidaRag(anyString());
        verify(this.entrevistaDao, times(1)).crearEntrevistaBase(anyString(), anyString(), any());
        verify(this.procesoEntrevistaDao, times(1)).crearEvento();
        verify(this.jmsPublisherClient, times(1)).enviarInformacionEmpresa(any());
    }

}