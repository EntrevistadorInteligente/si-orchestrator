package com.entrevistador.orquestador.dominio.service;

import com.entrevistador.orquestador.dominio.model.enums.EstadoProcesoEnum;
import com.entrevistador.orquestador.infrastructure.adapter.entity.ProcesoEntrevistaEntity;
import com.entrevistador.orquestador.infrastructure.adapter.repository.ProcesoEntrevistaRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ActualizarEstadoProcesoEntrevistaService {

    private final ProcesoEntrevistaRepository procesoEntrevistaRepository;

    public void ejecutar(String idEvento, EstadoProcesoEnum estadoProcesoEnum) {
        // TODO: obtener la entidad persistida de la bbdd
        this.procesoEntrevistaRepository.save(new ProcesoEntrevistaEntity());
    }

}
