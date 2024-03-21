package com.entrevistador.orquestador.dominio.model.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Preguntas {
    private List<String> preguntas;
}
