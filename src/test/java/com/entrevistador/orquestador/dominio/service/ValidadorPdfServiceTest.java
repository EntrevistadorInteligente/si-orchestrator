package com.entrevistador.orquestador.dominio.service;

import com.entrevistador.orquestador.utils.CustomFilePart;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.buffer.DefaultDataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ValidadorPdfServiceTest {

    private final ValidadorPdfService validadorPdfService = new ValidadorPdfService();

    @Test
    void ejecutarTest() {
        byte[] bytes = "Hello Test".getBytes();
        DefaultDataBuffer defaultDataBuffer = new DefaultDataBufferFactory().wrap(bytes);
        FilePart filePart = new CustomFilePart("file", defaultDataBuffer);

        Mono<byte[]> publisher = this.validadorPdfService.ejecutar(filePart);

        StepVerifier
                .create(publisher)
                .consumeNextWith(resultBytes -> {
                    assertArrayEquals(bytes, resultBytes);
                    assertEquals(new String(bytes), new String(resultBytes));
                })
                .verifyComplete();
    }


}