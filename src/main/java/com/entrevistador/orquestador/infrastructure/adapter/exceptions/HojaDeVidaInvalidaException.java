package com.entrevistador.orquestador.infrastructure.adapter.exceptions;

import com.entrevistador.orquestador.dominio.model.enums.CodigoErrorEnum;

public class HojaDeVidaInvalidaException extends RuntimeException{

    private final CodigoErrorEnum errorCode;

    public HojaDeVidaInvalidaException(CodigoErrorEnum errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public String getCode(){
        return errorCode.getCode();
    }
}