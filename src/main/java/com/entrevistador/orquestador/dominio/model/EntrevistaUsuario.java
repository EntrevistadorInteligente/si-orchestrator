package com.entrevistador.orquestador.dominio.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class EntrevistaUsuario {
    private String uuid;
    private String empresa;
    private String perfilEmpresa;
    private String seniorityEmpresa;
    private String pais;
    private String descripcionVacante;
    private String estadoEntrevista;

}
