package com.entrevistador.orquestador.dominio.excepciones;

import com.entrevistador.orquestador.dominio.model.enums.CodigoErrorEnum;

public class PdfException extends RuntimeException {
    private final CodigoErrorEnum errorCode;

    public PdfException(CodigoErrorEnum errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public String getCode(){
        return errorCode.getCode();
    }
}
