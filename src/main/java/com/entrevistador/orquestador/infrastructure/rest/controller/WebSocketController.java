package com.entrevistador.orquestador.infrastructure.rest.controller;

import com.entrevistador.orquestador.dominio.model.dto.Preguntas;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.websocket.ContainerProvider;
import jakarta.websocket.Session;
import jakarta.websocket.WebSocketContainer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/websocket")
public class WebSocketController {
    @PostMapping
    public ResponseEntity<String> simulaRecopilador(@RequestBody List<String> preguntas) {
        ObjectMapper mapper = new ObjectMapper();
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        String canal = "ws://localhost:8081/canalWS";

        try (Session session = container.connectToServer(ClienteEndpoint.class, new URI(canal))){
            String preguntasJson = mapper.writeValueAsString(new Preguntas(preguntas));
            session.getBasicRemote().sendText(preguntasJson);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok("PREGUNTAS ENVIADAS AL FRONT!");
    }
}
