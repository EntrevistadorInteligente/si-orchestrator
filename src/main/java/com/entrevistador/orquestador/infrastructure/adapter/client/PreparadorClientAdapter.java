package com.entrevistador.orquestador.infrastructure.adapter.client;

import com.entrevistador.orquestador.dominio.port.client.PreparadorClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class PreparadorClientAdapter implements PreparadorClient {

    private final WebClient webClient;

    public PreparadorClientAdapter(@Qualifier("webClientPreparador") WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Mono<Void> generarEntrevista() {
        return Mono.empty();
    }
}
