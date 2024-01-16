package com.entrevistador.orquestador.infrastructure.rest.controller;

import com.entrevistador.orquestador.application.usescases.SolicitudEntrevista;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;

@WebMvcTest(value = EntrevistaController.class)
class EntrevistaControllerTest {

    private final StringBuilder URL = new StringBuilder("/api/v1/entrevistador/orquestador");

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SolicitudEntrevista solicitudEntrevista;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("Debera cargar el CV")
    void shouldLoadCV_WhenPost() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "file.pdf",
                MediaType.APPLICATION_PDF_VALUE,
                "Nombre: Test".getBytes(StandardCharsets.UTF_8)
        );

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .multipart(this.URL.append("/cv").toString())
                .file(file);

        this.mockMvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string("Archivo PDF cargado con exito"));
    }

    @Test
    @DisplayName("Debera cargar la oferta laboral")
    void shouldLoadJobOffer_WhenPost() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "file.pdf",
                MediaType.APPLICATION_PDF_VALUE,
                "Nombre: Test".getBytes(StandardCharsets.UTF_8)
        );

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .multipart(this.URL.append("/oferta").toString())
                .file(file);

        this.mockMvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string("Archivo Oferta Laboral cargado con exito"));
    }

}