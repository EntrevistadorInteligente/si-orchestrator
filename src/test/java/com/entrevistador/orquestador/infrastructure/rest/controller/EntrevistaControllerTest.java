package com.entrevistador.orquestador.infrastructure.rest.controller;

import com.entrevistador.orquestador.application.usescases.SolicitudEntrevista;
import com.entrevistador.orquestador.dominio.model.Formulario;
import com.entrevistador.orquestador.infrastructure.adapter.dto.FeedbackUsuarioDto;
import com.entrevistador.orquestador.infrastructure.adapter.dto.FormularioDto;
import com.entrevistador.orquestador.infrastructure.adapter.dto.GenericResponse;
import com.entrevistador.orquestador.infrastructure.adapter.mapper.EntrevistaMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.hamcrest.Matchers.equalTo;
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
    private EntrevistaMapper mapper;

    @Test
    @DisplayName("Debera cargar el CV")
    void shouldLoadCV_WhenPost() {
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
                .expectBody(GenericResponse.class)
                .value(GenericResponse::getMessage, equalTo("Archivo PDF cargado con exito"));
    }

    @Test
    @DisplayName("Debera obtener el estado de la entrevista por id")
    void shouldGetInterviewStatusById_WhenGet() {
        when(this.solicitudEntrevista.obtenerEstadoEntrevistaPorId(any())).thenReturn(Mono.empty());

        this.webTestClient
                .get()
                .uri(URL.append("/1").toString())
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("Debera obtener el estado de la entrevista por usuario")
    void shouldGetInterviewStatusByUser_WhenGet() {
        when(this.solicitudEntrevista.obtenerEstadoEntrevistaPorUsuario(any())).thenReturn(Mono.empty());

        this.webTestClient
                .get()
                .uri(URL.append("?username=test").toString())
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("Debera terminar la entrevista")
    void shouldFinishInterview_WhenPut() {
        FeedbackUsuarioDto feedbackUsuarioDto = FeedbackUsuarioDto.builder().mensaje("Gracias por tu tiempo").build();

        when(this.solicitudEntrevista.terminarEntrevista(any(), any())).thenReturn(Mono.empty());

        this.webTestClient
                .put()
                .uri(URL.append("/1/terminar").toString())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(feedbackUsuarioDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(GenericResponse.class)
                .value(GenericResponse::getMessage, equalTo("Entrevista terminada con exito"));
    }
}