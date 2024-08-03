package com.entrevistador.orquestador.infrastructure.rest.controller;

import com.entrevistador.orquestador.application.usescases.HojaDeVida;
import com.entrevistador.orquestador.dominio.model.HojaDeVidaModel;
import com.entrevistador.orquestador.dominio.model.Perfil;
import com.entrevistador.orquestador.infrastructure.adapter.dto.GenericResponse;
import com.entrevistador.orquestador.infrastructure.adapter.dto.PerfilDto;
import com.entrevistador.orquestador.infrastructure.adapter.mapper.HojaDeVidaMapper;
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
    @MockBean
    private HojaDeVidaMapper mapper;

    @Test
    @DisplayName("Test para obtener la hoja de vida")
    void shouldGetCV_WhenGet() {
        HojaDeVidaModel perfilDto = HojaDeVidaModel.builder().uuid("any").build();

        when(this.hojaDeVida.obtenerHojaDeVida(anyString())).thenReturn(Mono.just(perfilDto));
        when(this.mapper.mapHojaDeVidaToPerfilDto(any()))
                .thenReturn(PerfilDto.builder().uuid(perfilDto.getUuid()).build());

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
        PerfilDto perfilDto = PerfilDto.builder()
                .uuid("any")
                .nombre("any")
                .perfil("any")
                .build();

        when(this.mapper.mapPerfilDtoToPerfil(any())).thenReturn(Perfil.builder().build());
        when(this.hojaDeVida.actualizarDatosPerfil(anyString(), any())).thenReturn(Mono.empty());

        this.webTestClient
                .put()
                .uri(URL.append("/any-any-any-any").toString())
                .body(Mono.just(perfilDto), PerfilDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(GenericResponse.class)
                .value(GenericResponse::getMessage, equalTo("Perfil actualizado con exito"));
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
        PerfilDto perfilDto = PerfilDto.builder().uuid("any").build();

        when(this.hojaDeVida.generarSolicitudHojaDeVida(any(), any())).thenReturn(Mono.just(HojaDeVidaModel.builder().build()));
        when(this.mapper.mapHojaDeVidaToPerfilDto(any())).thenReturn(perfilDto);

        MultipartBodyBuilder multipartRequest = new MultipartBodyBuilder();
        multipartRequest.part("file", new ByteArrayResource(file.getBytes())).contentType(MediaType.APPLICATION_PDF);
        multipartRequest.part("username", "any").contentType(MediaType.TEXT_PLAIN);

        this.webTestClient
                .post()
                .uri(URL.append("/cargas").toString())
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .bodyValue(multipartRequest.build())
                .exchange()
                .expectStatus().isCreated()
                .expectBody(PerfilDto.class)
                .value(PerfilDto::getUuid, equalTo(perfilDto.getUuid()));
    }
}