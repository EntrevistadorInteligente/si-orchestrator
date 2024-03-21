package com.entrevistador.orquestador.infrastructure.adapter.client;

import com.entrevistador.orquestador.dominio.model.dto.PreparacionEntrevistaDto;
import com.entrevistador.orquestador.dominio.port.client.AnalizadorClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AnalizadorClientAdapter implements AnalizadorClient {

    @Qualifier("webClientAnalizador")
    private final WebClient webClient;

    @Override
    public Mono<Void> enviarHojaDeVida(PreparacionEntrevistaDto preparacionEntrevistaDto) {
        return this.webClient
                .post()
                .uri("/api")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(preparacionEntrevistaDto), PreparacionEntrevistaDto.class)
                .retrieve()
                .bodyToMono(Void.class);
    }

}
