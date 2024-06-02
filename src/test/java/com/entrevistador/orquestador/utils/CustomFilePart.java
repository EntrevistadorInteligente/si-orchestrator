package com.entrevistador.orquestador.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.file.Path;

@RequiredArgsConstructor
public class CustomFilePart implements FilePart {

    private final String name;
    private final DataBuffer content;

    @Override
    public String filename() {
        return null;
    }

    @Override
    public Mono<Void> transferTo(Path dest) {
        return null;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public HttpHeaders headers() {
        return null;
    }

    @Override
    public Flux<DataBuffer> content() {
        return Flux.just(this.content);
    }

}
