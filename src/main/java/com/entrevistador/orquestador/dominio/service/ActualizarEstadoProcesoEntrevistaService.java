package com.entrevistador.orquestador.dominio.service;

import com.entrevistador.orquestador.dominio.model.dto.ProcesoEntrevistaDto;
import com.entrevistador.orquestador.dominio.model.enums.EstadoProcesoEnum;
import com.entrevistador.orquestador.infrastructure.adapter.entity.ProcesoEntrevistaEntity;
import com.entrevistador.orquestador.infrastructure.adapter.repository.ProcesoEntrevistaRepository;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@RequiredArgsConstructor
public class ActualizarEstadoProcesoEntrevistaService {

    private final Date date = new Date(new Date().getTime());
    private final ProcesoEntrevistaRepository procesoEntrevistaRepository;
    private ProcesoEntrevistaEntity procesoEntrevistaEntity;

    public void ejecutar(ProcesoEntrevistaDto procesoEntrevistaDto) { // (String idEvento, EstadoProcesoEnum estadoProcesoEnum)
        procesoEntrevistaEntity = ProcesoEntrevistaEntity.builder()
                 .procesoEntrevistaId(procesoEntrevistaDto.getUuid())
                 .fechaHora(date.toString())
                 .fuente(procesoEntrevistaDto.getFuente())
                 .estado(procesoEntrevistaDto.getEstado())
                 .error(procesoEntrevistaDto.getError())
                 .build();

        // TODO: obtener la entidad persistida de la bbdd
        this.procesoEntrevistaRepository.save(procesoEntrevistaEntity);
    }

}
