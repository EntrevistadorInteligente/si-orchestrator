package com.entrevistador.orquestador.dominio.model.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum FuenteEnum {
    ANALIZADOR(new EstadoProcesoEnum[] {EstadoProcesoEnum.AC, EstadoProcesoEnum.AOE,
            EstadoProcesoEnum.CVA, EstadoProcesoEnum.AE}),
    GENERADOR_FEEDBACK(new EstadoProcesoEnum[] {EstadoProcesoEnum.GF, EstadoProcesoEnum.GFG}),
    PREPARADOR_ENTREVISTA(new EstadoProcesoEnum[] {EstadoProcesoEnum.GP, EstadoProcesoEnum.OR,
            EstadoProcesoEnum.AR, EstadoProcesoEnum.AF});

    private List<EstadoProcesoEnum> estado;

    FuenteEnum(EstadoProcesoEnum[] estado){
        this.estado = Arrays.asList(estado);
    }

    public EstadoProcesoEnum validarEstado(FuenteEnum fuenteEnum, EstadoProcesoEnum estadoProcesoEnum){
        if(fuenteEnum.getEstado().contains(estadoProcesoEnum))
            return estadoProcesoEnum;
        return null;
    }

}