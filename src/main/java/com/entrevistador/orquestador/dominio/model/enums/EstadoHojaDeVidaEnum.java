package com.entrevistador.orquestador.dominio.model.enums;

import lombok.Getter;

@Getter
public enum EstadoHojaDeVidaEnum {
    US("USADO"),
    NU("NO USADO");

    private String descripcion;

    EstadoHojaDeVidaEnum(String descripcion) {
        this.descripcion = descripcion;
    }
}
