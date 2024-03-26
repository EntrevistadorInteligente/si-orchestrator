package com.entrevistador.orquestador.infrastructure.rest.controller;

import com.entrevistador.orquestador.dominio.port.sse.SseService;
import com.entrevistador.orquestador.infrastructure.adapter.sse.SseFluxService;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/v1/eventos")
public class SseController {

    private final SseService sseService;

    public SseController(SseFluxService sseService) {
        this.sseService = sseService;
    }

    @GetMapping(path = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>> subscribeToSse() {
        return sseService.getEventStream();
    }

}
