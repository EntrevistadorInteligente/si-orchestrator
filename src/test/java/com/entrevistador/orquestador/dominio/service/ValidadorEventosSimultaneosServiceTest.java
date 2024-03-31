package com.entrevistador.orquestador.dominio.service;

import com.entrevistador.orquestador.dominio.excepciones.CvOInfoEmpresaException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class ValidadorEventosSimultaneosServiceTest {
    @InjectMocks
    private ValidadorEventosSimultaneosService validadorEventosSimultaneosService;

    @Test
    void testEjecutar() {
        Mono<Boolean> publisher = this.validadorEventosSimultaneosService.ejecutar("any");

        StepVerifier
                .create(publisher)
                .expectNext(Boolean.FALSE)
                .verifyComplete();
    }

    @Test
    void testEjecutar_When_CvOInfoEmpresaException() {
        Mono<Boolean> publisher = this.validadorEventosSimultaneosService.ejecutar("");

        StepVerifier
                .create(publisher)
                .expectError(CvOInfoEmpresaException.class)
                .verify();
    }
}