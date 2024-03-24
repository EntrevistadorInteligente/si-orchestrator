package com.entrevistador.orquestador.dominio.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PosicionEntrevistaDto {
    @JsonProperty("id_entrevista")
    private String idEntrevista;
    @JsonProperty("evento_entrevista_id")
    private  String eventoEntrevistaId;
    @JsonProperty("formulario")
    private  FormularioDto formulario;
}
