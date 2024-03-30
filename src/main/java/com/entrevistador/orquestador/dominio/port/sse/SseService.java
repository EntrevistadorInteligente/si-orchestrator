package com.entrevistador.orquestador.dominio.port.sse;

import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Flux;

public interface SseService {
    void emitEvent(ServerSentEvent<String> event);
    Flux<ServerSentEvent<String>> getEventStream();
}
