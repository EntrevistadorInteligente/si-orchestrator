package com.entrevistador.orquestador.dominio.model.dto;


import lombok.Getter;

import java.util.Date;

@Getter
public class ProcesoEntrevistaDto {
    private String uuid;
    private Date fechaYHora;
    private String estado;
    private String fuente;
    private String error;
}
