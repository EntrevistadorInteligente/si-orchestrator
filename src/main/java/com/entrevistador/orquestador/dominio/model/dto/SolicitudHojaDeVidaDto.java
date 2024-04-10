package com.entrevistador.orquestador.dominio.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SolicitudHojaDeVidaDto {
    private String username;
    @JsonProperty("hoja_de_vida")
    private byte[] hojaDeVida;
}
