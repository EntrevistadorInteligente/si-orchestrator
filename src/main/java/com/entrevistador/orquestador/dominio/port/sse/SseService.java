package com.entrevistador.orquestador.dominio.port.sse;

import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SseService {
    Mono<Void> emitEvent(ServerSentEvent<String> event);

    Flux<ServerSentEvent<String>> getEventStream();
}
