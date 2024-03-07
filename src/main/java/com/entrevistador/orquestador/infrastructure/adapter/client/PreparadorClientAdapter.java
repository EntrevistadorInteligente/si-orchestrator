package com.entrevistador.orquestador.infrastructure.adapter.client;

import com.entrevistador.orquestador.dominio.port.client.PreparadorClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PreparadorClientAdapter implements PreparadorClient {

    private final WebClient webClient;

    @Override
    public Mono<Void> generarEntrevista() {
        return Mono.empty();
    }
}
