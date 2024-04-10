package com.entrevistador.orquestador.dominio.model;

import com.entrevistador.orquestador.dominio.model.dto.InformacionEmpresaDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class Entrevista {
    public String uuid;
    private String idEvento;
    private String idHojaDeVidaRag;
    private InformacionEmpresaDto informacionEmpresaDto;

    public String listToString(List<String> lista) {
        return String.join("-", lista);
    }

}
