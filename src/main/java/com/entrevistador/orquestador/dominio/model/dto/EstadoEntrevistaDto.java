package com.entrevistador.orquestador.dominio.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class EstadoEntrevistaDto {
    private String idEntrevista;
    private String estadoEntrevista;
}
