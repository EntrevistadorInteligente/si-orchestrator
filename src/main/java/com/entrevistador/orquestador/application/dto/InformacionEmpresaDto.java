package com.entrevistador.orquestador.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InformacionEmpresaDto {
    @JsonProperty("id_informacion_empresa_rag")
    private String idInformacionEmpresaRag;
    @JsonProperty("descripcion_vacante")
    private String descripcionVacante;
    private String empresa;
    private String perfil;
    private String seniority;
    private String pais;
}
