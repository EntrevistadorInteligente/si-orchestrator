package com.entrevistador.orquestador.dominio.model;

import lombok.Getter;

@Getter
public class Entrevista {
    public String uuid;
    private String type;
    private Object eventData;
}
