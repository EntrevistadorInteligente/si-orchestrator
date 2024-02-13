package com.entrevistador.orquestador.dominio.model.enums;

import lombok.Getter;

@Getter
public enum EstadoProcesoEnum {
    ANALIZADOR(new String[]{"Analizando CV", "Analizando oferta de empleo", "Comprobando validez de archivo", "Analizando empresas"}),
    GENERADOR_FEEDBACK(new String[]{"Generando feedback", "Generando feedback general"}),
    ORQUESTADOR(new String[]{""}),
    PREPARADOR_ENTREVISTA(new String[]{"Generando preguntas", "Obteniendo respuesta", "Analizando respuesta", "Acumular feedback"});

    String[] estados;

    private EstadoProcesoEnum(String[] estados){
        this.estados = estados;
    }

}
