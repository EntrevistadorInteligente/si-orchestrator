package com.entrevistador.orquestador.infrastructure.rest.controller;

import com.entrevistador.orquestador.application.usescases.EntrevistaPrueba;
import com.entrevistador.orquestador.dominio.model.IdEntrevista;
import com.entrevistador.orquestador.dominio.model.SoloPerfil;
import com.entrevistador.orquestador.infrastructure.adapter.dto.IdEntrevistaDto;
import com.entrevistador.orquestador.infrastructure.adapter.dto.SoloPerfilDto;
import com.entrevistador.orquestador.infrastructure.adapter.mapper.EntrevistaPruebaMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = VistaPreviaEntrevistaController.class)
class VistaPreviaEntrevistaControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private EntrevistaPrueba entrevistaPrueba;
    @MockBean
    private EntrevistaPruebaMapper mapper;

    private final StringBuilder URL = new StringBuilder("/v1/entrevistador/public");

    @Test
    @DisplayName("Debería mostrar la lista de perfiles")
    void testMostrarListaPerfiles() {
        SoloPerfilDto soloPerfilDto = SoloPerfilDto.builder().perfilEmpresa("any").build();

        when(this.entrevistaPrueba.getPerfiles()).thenReturn(Flux.just(SoloPerfil.builder().perfilEmpresa("any").build()));
        when(this.mapper.mapSoloPerfilToSoloPerfilDto(any())).thenReturn(soloPerfilDto);

        this.webTestClient
                .get()
                .uri(URL.append("/perfiles").toString())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(SoloPerfilDto.class)
                .hasSize(1);
    }

    @Test
    @DisplayName("Debería obtener el id de la entrevista")
    void testObtenerId() {
        IdEntrevistaDto idEntrevistaDto = IdEntrevistaDto.builder().id("any").build();

        when(this.entrevistaPrueba.getIdEntrevista(any())).thenReturn(Mono.just(IdEntrevista.builder().build()));
        when(this.mapper.mapIdEntrevistaDto(any())).thenReturn(idEntrevistaDto);

        this.webTestClient
                .get()
                .uri(URL.append("/entrevista_muestra_id?perfil=any").toString())
                .exchange()
                .expectStatus().isOk()
                .expectBody(IdEntrevistaDto.class)
                .value(IdEntrevistaDto::getId, equalTo(idEntrevistaDto.getId()));
    }
}