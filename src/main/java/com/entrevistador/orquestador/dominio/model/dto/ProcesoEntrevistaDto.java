package com.entrevistador.orquestador.dominio.model.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProcesoEntrevistaDto {
    private String uuid;
    private String fechaYHora;
    private String estado;
    private String fuente;
    private String error;
}
