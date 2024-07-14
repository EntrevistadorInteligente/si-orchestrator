package com.entrevistador.orquestador.infrastructure.rest.controller;

import com.entrevistador.orquestador.infrastructure.adapter.dto.FormularioDto;
import com.entrevistador.orquestador.application.usescases.SolicitudEntrevista;
import com.entrevistador.orquestador.dominio.model.Formulario;
import com.entrevistador.orquestador.infrastructure.adapter.mapper.EntrevistaMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = {EntrevistaController.class})
class EntrevistaControllerTest {

    private final StringBuilder URL = new StringBuilder("/v1/entrevistadores");

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private SolicitudEntrevista solicitudEntrevista;
    @MockBean
    private EntrevistaMapper    mapper;

    @Test
    @DisplayName("Debera cargar el CV")
    void shouldLoadCV_WhenPost() {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "file.pdf",
                MediaType.APPLICATION_PDF_VALUE,
                "Nombre: Test".getBytes(StandardCharsets.UTF_8)
        );

        when(this.solicitudEntrevista.generarSolicitudEntrevista(any(), any())).thenReturn(Mono.empty());
        when(this.mapper.mapFormularioDtoToFormulario(any())).thenReturn(Formulario.builder().build());


        this.webTestClient
                .post()
                .uri(URL.append("/solicitudes-entrevistas?username=test").toString())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(FormularioDto.builder()
                        .empresa("Tesla")
                        .perfil("Software Engineer Java")
                        .seniority("Senior")
                        .pais("Canada")
                        .descripcionVacante("Are you a tech professional")
                        .build())
                .exchange()
                .expectStatus().isCreated()
                .expectBody(String.class)
                .isEqualTo("{\"message\":\"Archivo PDF cargado con exito\"}");
    }

}