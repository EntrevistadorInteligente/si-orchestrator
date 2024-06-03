package com.entrevistador.orquestador.dominio.model.enums;

import lombok.Getter;
import java.text.MessageFormat;

public enum CodigoErrorEnum {

    ENTREVISTA_LIMITE_EXCEDIDO("E001", "No se pueden crear más entrevistas. Faltan {0} horas para desbloquear."),
    ENTREVISTA_EN_PROCESO("E002", "El usuario tiene una entrevista en proceso."),
    HOJA_DE_VIDA_INVALIDA("E003", "El documento no es valido"),
    SIZE_HOJA_DE_VIDA_EXCEDIDO("E004","Tamaño de archivo excedido"),
    INTERNAL_SERVER_ERROR("E500", "Internal Server Error");

    @Getter
    private final String code;
    private final String message;

    CodigoErrorEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage(Object... args) {
        return MessageFormat.format(message, args);
    }
}
