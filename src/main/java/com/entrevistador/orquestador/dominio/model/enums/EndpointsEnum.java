package com.entrevistador.orquestador.dominio.model.enums;

import lombok.Getter;

@Getter
public enum EndpointsEnum {
    NOTIFICADOR("webFluxNotificaciones");
    public final String descripcion;

    EndpointsEnum(String descripcion) {
        this.descripcion = descripcion;
    }
}
