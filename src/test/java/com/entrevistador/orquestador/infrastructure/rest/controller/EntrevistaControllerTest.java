package com.entrevistador.orquestador.infrastructure.rest.controller;

import com.entrevistador.orquestador.application.usescases.SolicitudEntrevista;
import com.entrevistador.orquestador.dominio.model.dto.FormularioDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebFluxTest
class EntrevistaControllerTest {

    private final StringBuilder URL = new StringBuilder("/api/v1/entrevistador/orquestador");

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private SolicitudEntrevista solicitudEntrevista;

    @Test
    @DisplayName("Debera cargar el CV")
    void shouldLoadCV_WhenPost() throws IOException {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "file.pdf",
                MediaType.APPLICATION_PDF_VALUE,
                "Nombre: Test".getBytes(StandardCharsets.UTF_8)
        );

        when(this.solicitudEntrevista.generarSolicitudEntrevista(any(), any())).thenReturn(Mono.empty());

        MultipartBodyBuilder multipartRequest = new MultipartBodyBuilder();
        multipartRequest.part("file", new ByteArrayResource(file.getBytes())).contentType(MediaType.APPLICATION_PDF);
        multipartRequest.part("formulario", new FormularioDto()).contentType(MediaType.APPLICATION_JSON);

        this.webTestClient
                .post()
                .uri(URL.append("/cv").toString())
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .bodyValue(multipartRequest.build())
                .exchange()
                .expectStatus().isCreated()
                .expectBody(String.class)
                .isEqualTo("Archivo PDF cargado con exito");
    }

}