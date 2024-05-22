package com.entrevistador.orquestador.dominio.model;

import com.entrevistador.orquestador.dominio.model.dto.InformacionEmpresaDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Entrevista {
    private String uuid;
    private String idEvento;
    private String idHojaDeVidaRag;
    private boolean isHojaDeVidaValida;
    private InformacionEmpresaDto informacionEmpresaDto;

}
