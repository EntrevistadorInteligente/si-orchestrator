package com.entrevistador.orquestador.dominio.model.enums;

import lombok.Getter;

@Getter
public enum EstadoHojaDeVidaEnum {
    AT("ANTIGUO"),
    NV("NUEVO");

    private String descripcion;

    EstadoHojaDeVidaEnum(String descripcion) {
        this.descripcion = descripcion;
    }
}
