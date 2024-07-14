package com.entrevistador.orquestador.dominio.excepciones;

public class ErrorDeserializarJson extends RuntimeException{

    public ErrorDeserializarJson(String message, Throwable e){
        super(message, e);
    }
}
