package com.entrevistador.orquestador.infrastructure.adapter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class EstadoEntrevistaDto {
    @JsonProperty("idEntrevista")
    private String idEntrevista;
    @JsonProperty("estadoEntrevista")
    private String estadoEntrevista;
    private String username;
}
