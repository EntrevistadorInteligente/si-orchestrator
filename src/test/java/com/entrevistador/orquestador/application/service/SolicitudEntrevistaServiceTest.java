package com.entrevistador.orquestador.application.service;

import com.entrevistador.orquestador.dominio.model.dto.FormularioDto;
import com.entrevistador.orquestador.dominio.model.dto.ProcesoEntrevistaDto;
import com.entrevistador.orquestador.dominio.port.ProcesoEntrevistaDao;
import com.entrevistador.orquestador.dominio.port.client.AnalizadorClient;
import com.entrevistador.orquestador.dominio.port.client.RecopiladorEmpresaClient;
import com.entrevistador.orquestador.dominio.service.CrearEntrevistaService;
import com.entrevistador.orquestador.dominio.service.ValidadorPdfService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SolicitudEntrevistaServiceTest {

    @InjectMocks
    private SolicitudEntrevistaService solicitudEntrevistaService;
    @Mock
    private AnalizadorClient analizadorClient;
    @Mock
    private ValidadorPdfService validadorPdfService;
    @Mock
    private ProcesoEntrevistaDao procesoEntrevistaDao;
    @Mock
    private CrearEntrevistaService crearEntrevistaService;
    @Mock
    private RecopiladorEmpresaClient recopiladorEmpresaClient;

    @Test
    void generarSolicitudEntrevistaTest() {
        when(this.validadorPdfService.ejecutar(any())).thenReturn(Mono.just(new byte[]{}));
        when(this.crearEntrevistaService.ejecutar()).thenReturn(Mono.just("any"));
        when(this.procesoEntrevistaDao.crearEvento()).thenReturn(Mono.just(ProcesoEntrevistaDto.builder().build()));
        when(this.analizadorClient.enviarHojaDeVida(any())).thenReturn(Mono.empty());
        when(this.recopiladorEmpresaClient.enviarInformacionEmpresa(any())).thenReturn(Mono.empty());

        Mono<Void> publisher = this.solicitudEntrevistaService.generarSolicitudEntrevista(Mono.just(mock(FilePart.class)), new FormularioDto());

        StepVerifier
                .create(publisher)
                .verifyComplete();

        verify(this.validadorPdfService, times(1)).ejecutar(any());
        verify(this.crearEntrevistaService, times(1)).ejecutar();
        verify(this.procesoEntrevistaDao, times(1)).crearEvento();
        verify(this.analizadorClient, times(1)).enviarHojaDeVida(any());
        verify(this.recopiladorEmpresaClient, times(1)).enviarInformacionEmpresa(any());
    }
}