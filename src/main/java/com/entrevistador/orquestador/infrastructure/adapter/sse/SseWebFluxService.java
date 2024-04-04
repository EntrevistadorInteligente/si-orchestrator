package com.entrevistador.orquestador.infrastructure.adapter.sse;

import com.entrevistador.orquestador.dominio.port.sse.SseService;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Service
public class SseWebFluxService implements SseService {

    private final Sinks.Many<ServerSentEvent<String>> sink;
    private final Flux<ServerSentEvent<String>> flux;

    public SseWebFluxService() {
        this.sink = Sinks.many().multicast().onBackpressureBuffer();
        this.flux = this.sink.asFlux().publish().autoConnect();
    }

    public Mono<Void> emitEvent(ServerSentEvent<String> event) {
        this.sink.tryEmitNext(event);
        return Mono.empty();
    }

    public Flux<ServerSentEvent<String>> getEventStream() {
        return this.flux;
    }
}