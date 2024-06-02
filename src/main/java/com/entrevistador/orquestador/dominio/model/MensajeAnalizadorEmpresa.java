package com.entrevistador.orquestador.dominio.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MensajeAnalizadorEmpresa {
    private ProcesoEntrevista procesoEntrevista;
    private String               idEntrevista;
    private String               idInformacionEmpresaRag;
}
