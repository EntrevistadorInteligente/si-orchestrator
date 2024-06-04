package com.entrevistador.orquestador.dominio.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class Entrevista {
    private String             uuid;
    private String             idEvento;
    private String             idHojaDeVidaRag;
    private boolean            isHojaDeVidaValida;
    private InformacionEmpresa informacionEmpresa;
    private LocalDateTime      fechaCreacion;

}
