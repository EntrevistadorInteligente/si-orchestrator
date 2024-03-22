package com.entrevistador.orquestador.infrastructure.adapter.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

@Slf4j
//@Service
public class WSHandler implements WebSocketHandler {

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        return session.receive()
                .doOnNext(WebSocketMessage::getPayloadAsText)
                .doOnNext(System.out::println)
                .then();
    }

}
