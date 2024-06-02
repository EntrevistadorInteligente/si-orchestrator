package com.entrevistador.orquestador.infrastructure.adapter.dto;

import com.entrevistador.orquestador.dominio.model.enums.EstadoProcesoEnum;
import com.entrevistador.orquestador.dominio.model.enums.FuenteEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProcesoEntrevistaDto {
    @JsonProperty("uuid")
    private String uuid;
    @JsonProperty("fecha_y_hora")
    private Date fechaYHora;
    @JsonProperty("estado")
    private EstadoProcesoEnum estado;
    @JsonProperty("fuente")
    private FuenteEnum fuente;
    @JsonProperty("error")
    private String error;
}
