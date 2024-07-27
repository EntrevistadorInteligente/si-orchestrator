package com.entrevistador.orquestador.infrastructure.adapter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EntrevistaUsuarioDto {
    private String uuid;
    private String empresa;
    private String perfilEmpresa;
    private String seniorityEmpresa;
    private String pais;
    private String descripcionVacante;
    private String estadoEntrevista;
}
