package com.entrevistador.orquestador.dominio.service;

import com.entrevistador.orquestador.dominio.excepciones.IdEstadoException;
import com.entrevistador.orquestador.dominio.model.dto.ProcesoEntrevistaDto;
import com.entrevistador.orquestador.dominio.model.enums.EstadoProcesoEnum;
import com.entrevistador.orquestador.dominio.model.enums.FuenteEnum;
import com.entrevistador.orquestador.infrastructure.adapter.repository.ProcesoEntrevistaRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;


@RequiredArgsConstructor
public class ActualizarEstadoProcesoEntrevistaService {

    private final ProcesoEntrevistaRepository procesoEntrevistaRepository;

    public void ejecutar(String idEvento, EstadoProcesoEnum estadoProcesoEnum) {
        var procesoEntrevistaEntity = this.procesoEntrevistaRepository.getReferenceById(idEvento);
        if (procesoEntrevistaEntity == null){
            throw new IdEstadoException("Id de estado no encontrado. ID: "+idEvento);
        } else {
            procesoEntrevistaEntity.actualizar(ProcesoEntrevistaDto
                    .builder()
                    .uuid("")
                    .fechaYHora("")
                    .fuente(FuenteEnum.ANALIZADOR)
                    .estado(EstadoProcesoEnum.AR)
                    .error("")
                    .build());
        }

        this.procesoEntrevistaRepository.save(procesoEntrevistaEntity);
    }

}
