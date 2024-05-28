package com.entrevistador.orquestador.infrastructure.adapter.exceptions;

public class ErrorDeserializarJson extends RuntimeException{

    public ErrorDeserializarJson(String message, Throwable e){
        super(message, e);
    }
}
