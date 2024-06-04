package com.entrevistador.orquestador.dominio.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PosicionEntrevista {
    private String             idEntrevista;
    private String             eventoEntrevistaId;
    private InformacionEmpresa formulario;
}
