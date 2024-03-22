package com.entrevistador.orquestador.infrastructure.rest.controller;

import com.entrevistador.orquestador.dominio.model.dto.Preguntas;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/websocket")
public class WebSocketController {

    @PostMapping
    public Mono<String> simulaRecopilador(@RequestBody List<String> preguntas) {
        WebSocketClient client = new ReactorNettyWebSocketClient();
        return client.execute(
                        URI.create("ws://localhost:8081/ws"),
                        session -> session.send(
                                        Mono.just(session.textMessage(this.listToString(preguntas))))
                                .thenMany(session.receive()
                                        .map(WebSocketMessage::getPayloadAsText)
                                        .log())
                                .then())
                .thenReturn("PREGUNTAS ENVIADAS AL FRONT!");
    }

    private String listToString(List<String> input) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(new Preguntas(input));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
