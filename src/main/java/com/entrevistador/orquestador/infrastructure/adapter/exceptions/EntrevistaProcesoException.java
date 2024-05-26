package com.entrevistador.orquestador.infrastructure.adapter.exceptions;

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
