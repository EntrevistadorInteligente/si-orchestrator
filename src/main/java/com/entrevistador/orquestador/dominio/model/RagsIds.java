package com.entrevistador.orquestador.dominio.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RagsIds {
    private String  idHojaDeVidaRag;
    private String  idInformacionEmpresaRag;
    private String  username;
    private Boolean hojaDeVidaValida;
}
