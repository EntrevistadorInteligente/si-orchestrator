package com.entrevistador.orquestador.dominio.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

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
