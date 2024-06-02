package com.entrevistador.orquestador.dominio.excepciones;

public class UsernameNoEncontradoException extends RuntimeException {
    public UsernameNoEncontradoException(String message) {
        super(message);
    }
}
