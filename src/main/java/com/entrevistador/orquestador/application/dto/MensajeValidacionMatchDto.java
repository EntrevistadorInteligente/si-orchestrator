package com.entrevistador.orquestador.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MensajeValidacionMatchDto {
    @JsonProperty("id_entrevista")
    private String idEntrevista;
    @JsonProperty("match_valido")
    private boolean matchValido;
    @JsonProperty("razon_validacion")
    private String razonValidacion;
}
