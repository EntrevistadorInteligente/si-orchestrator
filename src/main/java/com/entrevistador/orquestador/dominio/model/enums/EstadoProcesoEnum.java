package com.entrevistador.orquestador.dominio.model.enums;

import lombok.Getter;

@Getter
public enum EstadoProcesoEnum {
    AC("Analizando CV"),
    CVA("Comprobando validez de archivo"),

    AOE("Analizando oferta de empleo"),
    AE("Analizando empresas"),

    GF("Generando feedback"),
    GFG("Generando feedback general"),

    GP("Generando preguntas"),
    OR("Obteniendo respuesta"),
    AR("Analizando respuesta"),
    AF("Acumular feedback"),

    FN("Finalizado");

    private String descripcion;

    EstadoProcesoEnum(String descripcion) {
        this.descripcion = descripcion;
    }

}
