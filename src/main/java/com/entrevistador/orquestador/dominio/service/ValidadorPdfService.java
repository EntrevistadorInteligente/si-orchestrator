package com.entrevistador.orquestador.dominio.service;

import com.entrevistador.orquestador.dominio.model.enums.CodigoErrorEnum;
import com.entrevistador.orquestador.infrastructure.adapter.exceptions.HojaDeVidaInvalidaException;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

public class ValidadorPdfService {

    private static final byte[] PDF_HEADER = "%PDF-".getBytes(StandardCharsets.US_ASCII);

    public Mono<byte[]> ejecutar(FilePart file) {
        return DataBufferUtils.join(file.content())
                .flatMap(dataBuffer -> {
                    byte[] bytes = new byte[dataBuffer.readableByteCount()];
                    dataBuffer.read(bytes);
                    DataBufferUtils.release(dataBuffer);
                    if (esPdf(bytes)) {
                        return Mono.just(bytes);
                    } else {
                        return Mono.error(new HojaDeVidaInvalidaException(CodigoErrorEnum.HOJA_DE_VIDA_INVALIDA));
                    }
                });
    }

    private boolean esPdf(byte[] bytes) {
        if (bytes.length < PDF_HEADER.length) {
            return false;
        }
        for (int i = 0; i < PDF_HEADER.length; i++) {
            if (bytes[i] != PDF_HEADER[i]) {
                return false;
            }
        }
        return true;
    }

}
