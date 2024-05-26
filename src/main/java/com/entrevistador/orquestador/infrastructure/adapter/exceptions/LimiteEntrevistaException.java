package com.entrevistador.orquestador.infrastructure.adapter.exceptions;

import com.entrevistador.orquestador.dominio.model.enums.CodigoErrorEnum;

public class LimiteEntrevistaException  extends RuntimeException {

    private final CodigoErrorEnum errorCode;

    public LimiteEntrevistaException(CodigoErrorEnum errorCode, Object... args) {
        super(errorCode.getMessage(args));
        this.errorCode = errorCode;
    }

    public String getCode() {
        return errorCode.getCode();
    }

}
