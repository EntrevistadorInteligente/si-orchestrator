package com.entrevistador.orquestador.dominio.service;

import com.entrevistador.orquestador.dominio.excepciones.IdEstadoException;
import com.entrevistador.orquestador.dominio.model.dto.ProcesoEntrevistaDto;
import com.entrevistador.orquestador.dominio.port.ProcesoEntrevistaDao;
import com.entrevistador.orquestador.infrastructure.adapter.entity.ProcesoEntrevistaEntity;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class ActualizarEstadoProcesoEntrevistaService {

    private final ProcesoEntrevistaDao procesoEntrevistaDao;

    public void ejecutar(ProcesoEntrevistaDto procesoEntrevistaDtoParam) {
        final String mensajeExcepcion = "Id de estado no encontrado. ID: %s";
        ProcesoEntrevistaDto procesoEntrevistaDto = this.procesoEntrevistaDao
                .obtenerEventoPorId(procesoEntrevistaDtoParam.getUuid());

        if (procesoEntrevistaDto == null)
            throw new IdEstadoException(String.format(mensajeExcepcion, procesoEntrevistaDtoParam.getUuid()));

        procesoEntrevistaDto.actualizar(procesoEntrevistaDtoParam);
        this.procesoEntrevistaDao.actualizar(ProcesoEntrevistaEntity.builder()
                .procesoEntrevistaId(procesoEntrevistaDto.getUuid())
                .fechaHora(procesoEntrevistaDto.getFechaYHora())
                .estado(procesoEntrevistaDto.getEstado())
                .fuente(procesoEntrevistaDto.getFuente())
                .error(procesoEntrevistaDto.getError())
                .build());
    }

}