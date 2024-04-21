package com.entrevistador.orquestador.infrastructure.rest.controller;

import com.entrevistador.orquestador.application.usescases.HojaDeVida;
import com.entrevistador.orquestador.dominio.model.dto.PerfilDto;
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

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = {HojaDeVidaController.class})
class HojaDeVidaControllerTest {
    private final StringBuilder URL = new StringBuilder("/v1/hojas-de-vidas");

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private HojaDeVida hojaDeVida;

    @Test
    @DisplayName("Test para obtener la hoja de vida")
    void shouldGetCV_WhenGet() {
        PerfilDto perfilDto = PerfilDto.builder().uuid("any").build();

        when(this.hojaDeVida.obtenerHojaDeVida(anyString())).thenReturn(Mono.just(perfilDto));

        this.webTestClient
                .get()
                .uri(URL.append("/lucas").toString())
                .exchange()
                .expectStatus().isOk()
                .expectBody(PerfilDto.class)
                .value(PerfilDto::getUuid, equalTo(perfilDto.getUuid()));
    }

    @Test
    @DisplayName("Test Actualizar Datos de Perfil")
    void shouldUpgradeProfileData_WhenPut() {
        when(this.hojaDeVida.actualizarDatosPerfil(anyString(), any())).thenReturn(Mono.empty());

        this.webTestClient
                .put()
                .uri(URL.append("/any-any-any-any").toString())
                .body(Mono.just(PerfilDto.builder().build()), PerfilDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("Perfil actualizado con exito");
    }

    @Test
    @DisplayName("Test Carga de Hoja de Vida")
    void shouldLoadCV_WhenPost() throws IOException {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "file.pdf",
                MediaType.APPLICATION_PDF_VALUE,
                "Nombre: Test".getBytes(StandardCharsets.UTF_8)
        );

        when(this.hojaDeVida.generarSolicitudHojaDeVida(any(), any())).thenReturn(Mono.empty());

        MultipartBodyBuilder multipartRequest = new MultipartBodyBuilder();
        multipartRequest.part("file", new ByteArrayResource(file.getBytes())).contentType(MediaType.APPLICATION_PDF);
        multipartRequest.part("username", "any").contentType(MediaType.APPLICATION_JSON);

        this.webTestClient
                .post()
                .uri(URL.append("/cargas").toString())
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .bodyValue(multipartRequest.build())
                .exchange()
                .expectStatus().isCreated()
                .expectBody(String.class)
                .isEqualTo("Archivo PDF cargado con exito");
    }
}