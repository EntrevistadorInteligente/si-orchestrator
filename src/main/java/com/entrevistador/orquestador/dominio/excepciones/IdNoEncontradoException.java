package com.entrevistador.orquestador.dominio.excepciones;

public class IdNoEncontradoException extends RuntimeException {
    public IdNoEncontradoException(String message) {
        super(message);
    }
}
