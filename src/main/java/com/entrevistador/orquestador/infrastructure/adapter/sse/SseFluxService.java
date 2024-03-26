package com.entrevistador.orquestador.infrastructure.adapter.sse;

import com.entrevistador.orquestador.dominio.port.sse.SseService;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Service
public class SseFluxService implements SseService {
    private final Sinks.Many<ServerSentEvent<String>> sink;
    private final Flux<ServerSentEvent<String>> flux;

    public SseFluxService() {
        this.sink = Sinks.many().multicast().onBackpressureBuffer();
        this.flux = this.sink.asFlux().publish().autoConnect();
    }

    public void emitEvent(ServerSentEvent<String> event) {
        this.sink.tryEmitNext(event);
    }

    public Flux<ServerSentEvent<String>> getEventStream() {
        return this.flux;
    }
}
