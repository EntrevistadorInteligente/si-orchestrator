package com.entrevistador.orquestador.dominio.model.enums;

import lombok.Getter;

@Getter
public enum EndpointsEnum {
    NOTIFICADOR("webFluxNotificaciones"),
    ANALIZADOR("webFluxAnalizador");

    public final String descripcion;

    EndpointsEnum(String descripcion) {
        this.descripcion = descripcion;
    }
}
