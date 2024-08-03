package com.entrevistador.orquestador.infrastructure.adapter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SolicitudMatchDto {

    @JsonProperty("id_entrevista")
    private String idEntrevista;
    @JsonProperty("id_hoja_de_vida_rag")
    private String idHojaDeVidaRag;
    private FormularioDto formulario;

}
