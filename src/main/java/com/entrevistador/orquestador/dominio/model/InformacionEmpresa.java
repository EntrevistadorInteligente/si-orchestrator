package com.entrevistador.orquestador.dominio.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InformacionEmpresa {
    private String idInformacionEmpresaRag;
    private String descripcionVacante;
    private String empresa;
    private String perfil;
    private String seniority;
    private String pais;
}
