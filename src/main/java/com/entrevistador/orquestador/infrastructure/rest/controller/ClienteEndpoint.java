package com.entrevistador.orquestador.infrastructure.rest.controller;

import jakarta.websocket.ClientEndpoint;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;

@ClientEndpoint
public class ClienteEndpoint {
    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Conexión establecida con el servidor");
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("Mensaje recibido del servidor: " + message);
    }
}
