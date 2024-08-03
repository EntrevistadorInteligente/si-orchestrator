package com.entrevistador.orquestador.infrastructure.adapter.client;

import com.entrevistador.orquestador.dominio.model.Notificacion;
import com.entrevistador.orquestador.infrastructure.adapter.mapper.NotificacionesMapper;
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

class NotificacionesRestClientTest {
    private static MockWebServer mockWebServer;

    private NotificacionesRestClient notificacionesRestClient;

    private final NotificacionesMapper mapper = Mappers.getMapper(NotificacionesMapper.class);

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
        notificacionesRestClient = new NotificacionesRestClient(WebClient.builder().baseUrl(baseUrl).build(), mapper);
    }

    @Test
    void testEnviar() throws InterruptedException {
        mockWebServer.enqueue(new MockResponse().setResponseCode(200));

        StepVerifier.create(this.notificacionesRestClient.enviar("userId", Notificacion.builder().build()))
                .expectComplete()
                .verify();

        RecordedRequest recordedRequest = mockWebServer.takeRequest();
        assertEquals("POST", recordedRequest.getMethod());
        assertEquals("/v1/eventos/enviar/userId", recordedRequest.getPath());
    }
}