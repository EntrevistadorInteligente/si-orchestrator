package com.entrevistador.orquestador.infrastructure.adapter.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
