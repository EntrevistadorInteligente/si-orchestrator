package com.entrevistador.orquestador.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MensajeAnalizadorEmpresaDto {

    @JsonProperty("proceso_entrevista")
    private ProcesoEntrevistaDto procesoEntrevista;
    @JsonProperty("id_entrevista")
    private String idEntrevista;
    @JsonProperty("id_informacion_empresa_rag")
    private String idInformacionEmpresaRag;
}
