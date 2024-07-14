package com.entrevistador.orquestador.dominio.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudGeneracionEntrevista {
    private String idEntrevista;
    private String idHojaDeVida;
    private String username;
    private String idInformacionEmpresa;
}
