package com.entrevistador.orquestador.infrastructure.adapter.client;

import com.entrevistador.orquestador.dominio.model.HojaDeVidaModel;
import com.entrevistador.orquestador.dominio.model.SolicitudHojaDeVida;
import com.entrevistador.orquestador.infrastructure.adapter.dto.HojaDeVidaDto;
import com.entrevistador.orquestador.infrastructure.adapter.mapper.EntrevistaMapper;
import com.entrevistador.orquestador.infrastructure.adapter.mapper.HojaDeVidaMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AnalizadorRestClientTest {
    private static MockWebServer mockWebServer;

    private AnalizadorRestClient analizadorRestClient;

    private final HojaDeVidaMapper mapper = Mappers.getMapper(HojaDeVidaMapper.class);
    private final EntrevistaMapper mapperEntrevista = Mappers.getMapper(EntrevistaMapper.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    static void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @BeforeEach
    void init() {
        String baseUrl = String.format("http://localhost:%s", mockWebServer.getPort());
        analizadorRestClient = new AnalizadorRestClient(WebClient.builder().baseUrl(baseUrl).build(), mapper, mapperEntrevista);
    }

    @Test
    void testEnviar() throws JsonProcessingException, InterruptedException {
        HojaDeVidaModel hojaDeVidaModel = HojaDeVidaModel.builder().nombre("any").build();
        HojaDeVidaDto hojaDeVidaDto = HojaDeVidaDto.builder().nombre("any").build();

        String jsonResponse = objectMapper.writeValueAsString(hojaDeVidaDto);

        MockResponse mockResponse = new MockResponse()
                .setBody(jsonResponse)
                .setResponseCode(201)
                .addHeader("Content-Type", "application/json");
        mockWebServer.enqueue(mockResponse);

        StepVerifier
                .create(this.analizadorRestClient.enviar(SolicitudHojaDeVida.builder().build()))
                .assertNext(response -> assertEquals(hojaDeVidaModel.getNombre(), response.getNombre()))
                .verifyComplete();

        RecordedRequest recordedRequest = mockWebServer.takeRequest();
        assertEquals("POST", recordedRequest.getMethod());
        assertEquals("/v1/analizador/hojas-de-vida/generar", recordedRequest.getPath());
    }

    @Test
    void testEnviar_Status400Or500() throws JsonProcessingException, InterruptedException {
        HojaDeVidaDto hojaDeVidaDto = HojaDeVidaDto.builder().nombre("any").build();

        String jsonResponse = objectMapper.writeValueAsString(hojaDeVidaDto);

        MockResponse mockResponse = new MockResponse()
                .setBody(jsonResponse)
                .setResponseCode(400)
                .addHeader("Content-Type", "application/json");
        mockWebServer.enqueue(mockResponse);

        StepVerifier
                .create(this.analizadorRestClient.enviar(SolicitudHojaDeVida.builder().build()))
                .expectError(RuntimeException.class)
                .verify();

        RecordedRequest recordedRequest = mockWebServer.takeRequest();
        assertEquals("POST", recordedRequest.getMethod());
        assertEquals("/v1/analizador/hojas-de-vida/generar", recordedRequest.getPath());
    }
}