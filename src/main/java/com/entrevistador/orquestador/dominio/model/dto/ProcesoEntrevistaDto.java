package com.entrevistador.orquestador.dominio.model.dto;

import com.entrevistador.orquestador.dominio.excepciones.ActualizarEstadoEntrevistaException;
import com.entrevistador.orquestador.dominio.model.enums.EstadoProcesoEnum;
import com.entrevistador.orquestador.dominio.model.enums.FuenteEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.util.Date;

@Getter
@Builder
@AllArgsConstructor
public class ProcesoEntrevistaDto {
    private String uuid;
    private Date fechaYHora;
    private EstadoProcesoEnum estado;
    private FuenteEnum fuente;
    private String error;

    public boolean actualizar(ProcesoEntrevistaDto procesoEntrevistaDto){
        this.fechaYHora= new Date();

        if(procesoEntrevistaDto.getEstado() == null)
            throw new ActualizarEstadoEntrevistaException("Error al intentar actualizar el " +
                    "estado. El dato \"estado\" fue nulo.");
        this.estado = procesoEntrevistaDto.getEstado();

        if(procesoEntrevistaDto.getFuente() == null)
            throw new ActualizarEstadoEntrevistaException("Error al intentar actualizar el " +
                    "estado. El dato \"fuente\" fue nulo.");
        this.fuente = procesoEntrevistaDto.getFuente();

        if(procesoEntrevistaDto.getFuente().validarEstado(procesoEntrevistaDto.getFuente(),
                procesoEntrevistaDto.getEstado()) == null)
            throw new ActualizarEstadoEntrevistaException("Error al intentar actualizar el " +
                    "estado. El estado ingresado no pertenece a la fuente ingresada.");

        this.error = procesoEntrevistaDto.getError();

        return true;
    }
}
