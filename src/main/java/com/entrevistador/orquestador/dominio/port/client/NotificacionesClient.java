package com.entrevistador.orquestador.dominio.port.client;

import com.entrevistador.orquestador.dominio.model.dto.NotifiacionDto;
import reactor.core.publisher.Mono;

public interface NotificacionesClient {
    Mono<Void> enviar(String userId, NotifiacionDto notifiacionDto);
}
