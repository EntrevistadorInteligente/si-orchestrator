package com.entrevistador.orquestador.dominio.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackComentario {
    private String idPregunta;
    private String feedback;
    private String score;
}
