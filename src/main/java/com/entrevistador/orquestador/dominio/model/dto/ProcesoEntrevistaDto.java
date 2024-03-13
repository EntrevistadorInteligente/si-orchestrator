package com.entrevistador.orquestador.dominio.model.dto;

import com.entrevistador.orquestador.dominio.model.enums.EstadoProcesoEnum;
import com.entrevistador.orquestador.dominio.model.enums.FuenteEnum;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProcesoEntrevistaDto {
    private String uuid;
    private String fechaYHora;
    private EstadoProcesoEnum estado;
    private FuenteEnum fuente;
    private String error;
}
