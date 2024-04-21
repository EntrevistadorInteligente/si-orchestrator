package com.entrevistador.orquestador.dominio.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MensajeValidacionMatch {
    @JsonProperty("id_entrevista")
    private String idEntrevista;
    @JsonProperty("match_valido")
    private boolean matchValido;
    @JsonProperty("razon_validacion")
    private String razonValidacion;
}
