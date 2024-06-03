package com.entrevistador.orquestador.dominio.service;

import com.entrevistador.orquestador.dominio.model.enums.CodigoErrorEnum;
import com.entrevistador.orquestador.infrastructure.adapter.exceptions.HojaDeVidaInvalidaException;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.stream.IntStream;

public class ValidadorPdfService {

    private static final byte[] PDF_HEADER = "%PDF-".getBytes(StandardCharsets.US_ASCII);
    private static final long MAX_FILE_SIZE = 3 * 1024 * 1024; // 3MB en bytes
    private static final int HEADER_LENGTH = PDF_HEADER.length;

    public Mono<byte[]> ejecutar(FilePart file) {
        return DataBufferUtils.join(file.content())
                .flatMap(dataBuffer -> {
                    byte[] bytes = new byte[dataBuffer.readableByteCount()];
                    dataBuffer.read(bytes);
                    DataBufferUtils.release(dataBuffer);
                    if(!esPdf(bytes)){
                        return Mono.error(new HojaDeVidaInvalidaException(CodigoErrorEnum.HOJA_DE_VIDA_INVALIDA));
                    }
                    if (bytes.length > MAX_FILE_SIZE) {
                        return Mono.error(new HojaDeVidaInvalidaException(CodigoErrorEnum.SIZE_HOJA_DE_VIDA_EXCEDIDO));
                    }
                    return Mono.just(bytes);
                });
    }

    private boolean esPdf(byte[] bytes) {
        if (bytes.length < HEADER_LENGTH) {
            return false;
        }
        return IntStream.range(0,HEADER_LENGTH)
                .allMatch(i -> bytes[i] == PDF_HEADER[i]);
    }

}
