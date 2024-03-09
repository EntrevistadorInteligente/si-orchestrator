package com.entrevistador.orquestador.infrastructure.adapter.client;

import com.entrevistador.orquestador.dominio.model.dto.FormularioDto;
import com.entrevistador.orquestador.dominio.port.client.RecopiladorEmpresaClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class RecopiladorEmpresaClientAdapter implements RecopiladorEmpresaClient {

    private final WebClient webClient;

    public RecopiladorEmpresaClientAdapter(@Qualifier("webClientRecopilador") WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Mono<Void> enviarInformacionEmpresa(FormularioDto formulario) {
        return this.webClient
                .post()
                .uri("/api")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(formulario), FormularioDto.class)
                .retrieve()
                .bodyToMono(Void.class);
    }

}
