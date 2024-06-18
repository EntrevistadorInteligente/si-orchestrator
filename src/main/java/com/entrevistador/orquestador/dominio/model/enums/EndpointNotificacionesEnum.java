package com.entrevistador.orquestador.dominio.model.enums;

import lombok.Getter;

@Getter
public enum EndpointNotificacionesEnum {
    ENVIAR_EVENTO("enviarEvento");

    public final String descripcion;

    EndpointNotificacionesEnum(String descripcion) {
        this.descripcion = descripcion;
    }
}
