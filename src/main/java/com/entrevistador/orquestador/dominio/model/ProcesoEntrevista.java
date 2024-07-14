package com.entrevistador.orquestador.dominio.model;

import com.entrevistador.orquestador.dominio.excepciones.ActualizarEstadoEntrevistaException;
import com.entrevistador.orquestador.dominio.model.enums.EstadoProcesoEnum;
import com.entrevistador.orquestador.dominio.model.enums.FuenteEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcesoEntrevista {
    private String uuid;
    private Date fechaYHora;
    private EstadoProcesoEnum estado;
    private FuenteEnum fuente;
    private String error;

    public boolean actualizar(ProcesoEntrevista procesoEntrevista) {
        final String mensajeErrorAlActualizar = "Error al intentar actualizar el estado. %s";
        this.fechaYHora = new Date();

        if (procesoEntrevista.getEstado() == null)
            throw new ActualizarEstadoEntrevistaException(String.format(mensajeErrorAlActualizar,
                    "El dato \"estado\" fue nulo."));
        this.estado = procesoEntrevista.getEstado();

        if (procesoEntrevista.getFuente() == null)
            throw new ActualizarEstadoEntrevistaException(String.format(mensajeErrorAlActualizar,
                    "El dato \"fuente\" fue nulo."));
        this.fuente = procesoEntrevista.getFuente();

        if (procesoEntrevista.getFuente().validarEstado(procesoEntrevista.getFuente(),
                procesoEntrevista.getEstado()) == null)
            throw new ActualizarEstadoEntrevistaException(String.format(mensajeErrorAlActualizar,
                    "El estado ingresado no pertenece a la fuente ingresada."));

        this.error = procesoEntrevista.getError();

        return true;
    }
}
