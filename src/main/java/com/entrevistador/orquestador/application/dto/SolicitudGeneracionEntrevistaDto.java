package com.entrevistador.orquestador.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SolicitudGeneracionEntrevistaDto {
    @JsonProperty("id_entrevista")
    private String idEntrevista;
    @JsonProperty("id_hoja_de_vida")
    private String idHojaDeVida;
    private String username;
    @JsonProperty("id_informacion_empresa")
    private String idInformacionEmpresa;
}
