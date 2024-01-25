package com.entrevistador.orquestador.infrastructure.adapter.client;

import com.entrevistador.orquestador.dominio.model.dto.FormularioDto;
import com.entrevistador.orquestador.dominio.port.client.RecopiladorEmpresaClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RecopiladorEmpresaClientImp implements RecopiladorEmpresaClient {

    private final WebClient webClient;

    @Override
    public Mono<Void> enviarInformacionEmpresa(FormularioDto formulario) {
        return this.webClient
                .post()
                .uri("URI")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(formulario), FormularioDto.class)
                .retrieve()
                .bodyToMono(Void.class);
    }

}
