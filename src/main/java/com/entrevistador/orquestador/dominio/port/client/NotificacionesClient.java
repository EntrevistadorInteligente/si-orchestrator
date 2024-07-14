package com.entrevistador.orquestador.dominio.port.client;

import com.entrevistador.orquestador.dominio.model.Notificacion;
import reactor.core.publisher.Mono;

public interface NotificacionesClient {
    Mono<Void> enviar(String userId, Notificacion notificacion);
}
