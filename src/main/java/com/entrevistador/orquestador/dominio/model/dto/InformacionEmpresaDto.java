package com.entrevistador.orquestador.dominio.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InformacionEmpresaDto {
    @JsonProperty("id_informacion_empresa_rag")
    private String idInformacionEmpresaRag;
    private String empresa;
    private String perfil;
    private String seniority;
    private String pais;
    private List<String> preguntas;
}
