package com.entrevistador.orquestador.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PreparacionEntrevistaDto {
    @JsonProperty("id_entrevista")
    private String idEntrevista;
    @JsonProperty("evento_entrevista_id")
    private String eventoEntrevistaId;
    @JsonProperty("hoja_de_vida")
    private byte[] hojaDeVida;
}
