package com.entrevistador.orquestador.infrastructure.adapter.client;

import com.entrevistador.orquestador.dominio.model.dto.PreparacionEntrevistaDto;
import com.entrevistador.orquestador.dominio.port.client.AnalizadorClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class AnalizadorClientAdapter {

    private final WebClient webClient;

    public AnalizadorClientAdapter(@Qualifier("webClientAnalizador") WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<Void> enviarHojaDeVida(PreparacionEntrevistaDto preparacionEntrevistaDto) {
        var asd =this.webClient
                .post()
                .uri("/procesar-cv")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(preparacionEntrevistaDto), PreparacionEntrevistaDto.class)
                .retrieve()
                .bodyToMono(Void.class);
        return asd;
    }

}
