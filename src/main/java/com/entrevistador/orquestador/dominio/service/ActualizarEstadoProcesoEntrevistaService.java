package com.entrevistador.orquestador.dominio.service;

import com.entrevistador.orquestador.dominio.excepciones.IdEstadoException;
import com.entrevistador.orquestador.dominio.model.enums.EstadoProcesoEnum;
import com.entrevistador.orquestador.infrastructure.adapter.entity.ProcesoEntrevistaEntity;
import com.entrevistador.orquestador.infrastructure.adapter.repository.ProcesoEntrevistaRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;


@RequiredArgsConstructor
public class ActualizarEstadoProcesoEntrevistaService {

    private final ProcesoEntrevistaRepository procesoEntrevistaRepository;
    private ProcesoEntrevistaEntity procesoEntrevistaEntity;

    public void ejecutar(String idEvento, EstadoProcesoEnum estadoProcesoEnum) {
        this.procesoEntrevistaEntity = this.procesoEntrevistaRepository.getReferenceById(idEvento);

        if (this.procesoEntrevistaEntity == null){
            throw new IdEstadoException("Id de estado no encontrado. ID: "+idEvento);
        } else {
            this.procesoEntrevistaEntity.setFechaHora(LocalDate.now().toString());
            this.procesoEntrevistaEntity.setEstado("");
            this.procesoEntrevistaEntity.setFuente("");
            this.procesoEntrevistaEntity.setError("");
        }

        this.procesoEntrevistaRepository.save(procesoEntrevistaEntity);
    }

}
