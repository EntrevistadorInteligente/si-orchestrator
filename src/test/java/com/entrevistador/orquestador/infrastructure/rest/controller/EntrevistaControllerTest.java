package com.entrevistador.orquestador.infrastructure.rest.controller;

import com.entrevistador.orquestador.application.usescases.SolicitudEntrevista;
import com.entrevistador.orquestador.dominio.model.dto.FormularioDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = {EntrevistaController.class})
class EntrevistaControllerTest {
    private final StringBuilder URL = new StringBuilder("/v1/entrevistadores");

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private SolicitudEntrevista solicitudEntrevista;

    @Test
    @DisplayName("Debera cargar el CV")
    void shouldLoadCV_WhenPost() {
        when(this.solicitudEntrevista.generarSolicitudEntrevista(any(), any())).thenReturn(Mono.empty());

        this.webTestClient
                .post()
                .uri(URL.append("/solicitudes-entrevistas?username=any").toString())
                .body(Mono.just(FormularioDto.builder().build()), FormularioDto.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(String.class)
                .isEqualTo("Archivo PDF cargado con exito");
    }
}