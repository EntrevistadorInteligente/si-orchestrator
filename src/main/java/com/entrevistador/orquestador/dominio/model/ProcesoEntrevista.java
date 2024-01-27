package com.entrevistador.orquestador.dominio.model;

import lombok.Getter;

import java.util.Date;

@Getter
public class ProcesoEntrevista {
    private String uuid;
    private Date fechaYHora;
    private String estado;
    private String fuente;
    private String error;
}
