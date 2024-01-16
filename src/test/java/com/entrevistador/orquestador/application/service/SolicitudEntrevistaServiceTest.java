package com.entrevistador.orquestador.application.service;

import com.entrevistador.orquestador.dominio.port.ProcesoEntrevistaDao;
import com.entrevistador.orquestador.dominio.service.CrearEntrevistaService;
import com.entrevistador.orquestador.dominio.service.ValidadorPdfService;
import com.entrevistador.orquestador.infrastructure.adapter.client.AnalizadorClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.charset.StandardCharsets;

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

    @Test
    void testGenerarOfertaLaboral() {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "file.pdf",
                MediaType.APPLICATION_PDF_VALUE,
                "Nombre: Test".getBytes(StandardCharsets.UTF_8)
        );
        Mockito.when(this.validadorPdfService.ejecutar(ArgumentMatchers.any())).thenReturn(new byte[]{});

        this.solicitudEntrevistaService.generarOfertaLaboral(file);

        Mockito.verify(this.validadorPdfService, Mockito.times(1)).ejecutar(ArgumentMatchers.any());
        Mockito.verify(this.analizadorClient, Mockito.times(1)).enviarOfertaLaboral(ArgumentMatchers.any());
    }
}