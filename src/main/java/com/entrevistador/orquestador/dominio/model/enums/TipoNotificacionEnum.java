package com.entrevistador.orquestador.dominio.model.enums;

import lombok.Getter;

@Getter
public enum TipoNotificacionEnum {
    HG("HOJA_DE_VIDA_GENERADA");

    private String descripcion;

    TipoNotificacionEnum(String descripcion) {
        this.descripcion = descripcion;
    }

}
