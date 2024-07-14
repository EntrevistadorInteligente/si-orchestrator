package com.entrevistador.orquestador.infrastructure.adapter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PreguntaGeneradaDto {
    @JsonProperty("question")
    private String pregunta;
}
