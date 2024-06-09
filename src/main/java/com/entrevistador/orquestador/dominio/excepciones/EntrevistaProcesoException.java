package com.entrevistador.orquestador.dominio.excepciones;

import com.entrevistador.orquestador.dominio.model.enums.CodigoErrorEnum;

public class EntrevistaProcesoException extends RuntimeException {

    private final CodigoErrorEnum errorCode;

    public EntrevistaProcesoException(CodigoErrorEnum errorCode) {
        this.errorCode = errorCode;
    }

    public String getCode() {
        return errorCode.getCode();
    }

}
