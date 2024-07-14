package com.entrevistador.orquestador.dominio.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntrevistaModel {
    private String                 idEntrevista;
    private String                 username;
    private List<PreguntaGenerada> preguntas;
}
