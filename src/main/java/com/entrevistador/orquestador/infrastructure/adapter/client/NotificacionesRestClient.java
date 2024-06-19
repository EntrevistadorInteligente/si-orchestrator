package com.entrevistador.orquestador.infrastructure.adapter.client;

import com.entrevistador.orquestador.dominio.model.Notificacion;
import com.entrevistador.orquestador.infrastructure.adapter.dto.NotificacionDto;
import com.entrevistador.orquestador.dominio.port.client.NotificacionesClient;
import com.entrevistador.orquestador.infrastructure.adapter.mapper.NotificacionesMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificacionesRestClient implements NotificacionesClient {

    @Qualifier("webClientNotification")
    private final WebClient webClient;

    private final NotificacionesMapper mapper;

    @Override
    public Mono<Void> enviar(String userId, Notificacion notificacion) {
        return this.webClient.post()
                .uri("/v1/eventos/enviar/{userId}",userId)
                .bodyValue(this.mapper.mapNotificacionToNotificacionDto(notificacion))
                .retrieve()
                .bodyToMono(Void.class);
    }
}
