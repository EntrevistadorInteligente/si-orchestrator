package com.entrevistador.orquestador.dominio.model;

import com.entrevistador.orquestador.dominio.model.dto.InformacionEmpresaDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class Entrevista {
    public String uuid;
    private String idEvento;
    private String idHojaDeVidaRag;
    private boolean isHojaDeVidaValida;
    private InformacionEmpresaDto informacionEmpresaDto;
    private LocalDateTime fechaCreacion;

}
