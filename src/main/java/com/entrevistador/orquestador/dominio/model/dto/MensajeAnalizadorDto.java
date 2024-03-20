package com.entrevistador.orquestador.dominio.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MensajeAnalizadorDto {
    @JsonProperty("proceso_entrevista")
    private ProcesoEntrevistaDto procesoEntrevista;
    @JsonProperty("id_entrevista")
    private String idEntrevista;
    @JsonProperty("hoja_de_vida")
    private HojaDeVidaDto hojaDeVida;
}
