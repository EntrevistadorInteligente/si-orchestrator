package com.entrevistador.orquestador.dominio.model;

import com.entrevistador.orquestador.dominio.model.dto.HojaDeVidaDto;
import com.entrevistador.orquestador.dominio.model.dto.InformacionEmpresaDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Entrevista {
    public String uuid;
    private String idEvento;
    private HojaDeVidaDto hojaDeVidaDto;
    private InformacionEmpresaDto informacionEmpresaDto;
}
