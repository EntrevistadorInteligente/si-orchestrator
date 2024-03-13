package com.entrevistador.orquestador.dominio.model.enums;

import lombok.Getter;

@Getter
public enum EstadoProcesoEnum {
    AC("Analizando CV"),
    AOE("Analizando oferta de empleo"),
    CVA("Comprobando validez de archivo"),
    AE("Analizando empresas"),

    GF("Generando feedback"),
    GFG("Generando feedback general"),

    GP("Generando preguntas"),
    OR("Obteniendo respuesta"),
    AR("Analizando respuesta"),
    AF("Acumular feedback");

    private String descripcion;

    private EstadoProcesoEnum(String descripcion){
        this.descripcion = descripcion;
    }

}
