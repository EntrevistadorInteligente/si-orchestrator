package com.entrevistador.orquestador.dominio.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MensajeValidacionMatch {
    private String idEntrevista;
    private boolean matchValido;
    private String razonValidacion;
}
