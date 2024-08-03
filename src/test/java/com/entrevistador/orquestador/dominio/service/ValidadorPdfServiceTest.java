package com.entrevistador.orquestador.dominio.service;

import com.entrevistador.orquestador.dominio.excepciones.PdfException;
import com.entrevistador.orquestador.utils.CustomFilePart;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.buffer.DefaultDataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static com.entrevistador.orquestador.utils.PdfFileEm.PDF_FILE;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ValidadorPdfServiceTest {

    private final ValidadorPdfService validadorPdfService = new ValidadorPdfService();

    @Test
    void ejecutarTest() {
        byte[] bytes = PDF_FILE.getBytes();
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

    @Test
    void testEjecutarTest_PdfException_HojaDeVidaInvalida() {
        byte[] bytes = "any".getBytes();
        DefaultDataBuffer defaultDataBuffer = new DefaultDataBufferFactory().wrap(bytes);
        FilePart filePart = new CustomFilePart("file", defaultDataBuffer);

        Mono<byte[]> publisher = this.validadorPdfService.ejecutar(filePart);

        StepVerifier
                .create(publisher)
                .expectError(PdfException.class)
                .verify();
    }

    @Test
    void testEjecutarTest_PdfException_SizeHojaDeVidaExcedido() {
        byte[] bytes = new byte[4 * 1024 * 1024];
        bytes[0] = "%".getBytes()[0];
        bytes[1] = "P".getBytes()[0];
        bytes[2] = "D".getBytes()[0];
        bytes[3] = "F".getBytes()[0];
        bytes[4] = "-".getBytes()[0];
        DefaultDataBuffer defaultDataBuffer = new DefaultDataBufferFactory().wrap(bytes);
        FilePart filePart = new CustomFilePart("file", defaultDataBuffer);

        Mono<byte[]> publisher = this.validadorPdfService.ejecutar(filePart);

        StepVerifier
                .create(publisher)
                .expectError(PdfException.class)
                .verify();
    }
}