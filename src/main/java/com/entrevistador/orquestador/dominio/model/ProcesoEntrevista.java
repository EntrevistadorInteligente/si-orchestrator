package com.entrevistador.orquestador.dominio.model;

import com.entrevistador.orquestador.dominio.model.dto.ProcesoEntrevistaDto;
import com.entrevistador.orquestador.dominio.model.enums.EstadoProcesoEnum;
import com.entrevistador.orquestador.dominio.model.enums.FuenteEnum;
import lombok.Getter;

import java.util.Date;

@Getter
public class ProcesoEntrevista {
    private String uuid;
    private Date fechaYHora;
    private EstadoProcesoEnum estado;
    private FuenteEnum fuente;
    private String error;
}
