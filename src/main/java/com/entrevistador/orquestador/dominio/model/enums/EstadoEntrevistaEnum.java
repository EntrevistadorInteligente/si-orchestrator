package com.entrevistador.orquestador.dominio.model.enums;

import lombok.Getter;

@Getter
public enum EstadoEntrevistaEnum {
    IC("INICIADA"),
    PG("PREGUNTAS_GENERADAS"),
    FG("FEEDBACK_GENERADO"),
    FN("FINALIZADA");

    private String descripcion;

    EstadoEntrevistaEnum(String descripcion) {
        this.descripcion = descripcion;
    }

}
