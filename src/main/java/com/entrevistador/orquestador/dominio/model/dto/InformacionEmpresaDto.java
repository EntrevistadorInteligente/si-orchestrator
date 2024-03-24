package com.entrevistador.orquestador.dominio.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InformacionEmpresaDto {
    private String nombreEmpresa;
    private String puesto;
}
