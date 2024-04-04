package com.entrevistador.orquestador.dominio.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MensajeAnalizadorEmpresaDto {

    @JsonProperty("proceso_entrevista")
    private ProcesoEntrevistaDto procesoEntrevista;
    @JsonProperty("id_entrevista")
    private String idEntrevista;
    @JsonProperty("informacion_empresa")
    private InformacionEmpresaDto informacionEmpresa;
}
