package com.entrevistador.orquestador.dominio.service;

import com.entrevistador.orquestador.dominio.excepciones.IdNoEncontradoException;
import com.entrevistador.orquestador.dominio.model.dto.ProcesoEntrevistaDto;
import com.entrevistador.orquestador.dominio.port.ProcesoEntrevistaDao;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ActualizarEstadoProcesoEntrevistaService {

    private final ProcesoEntrevistaDao procesoEntrevistaDao;
    public Mono<Void> ejecutar(ProcesoEntrevistaDto procesoEntrevistaDtoParam) {
        return this.procesoEntrevistaDao
                .obtenerEventoPorId(procesoEntrevistaDtoParam.getUuid())
                .switchIfEmpty(Mono.error(new IdNoEncontradoException("Id de estado no encontrado. ID: " + procesoEntrevistaDtoParam.getUuid())))
                .doOnNext(procesoEntrevistaDto -> procesoEntrevistaDto.actualizar(procesoEntrevistaDtoParam))
                .flatMap(this.procesoEntrevistaDao::actualizar)
                .then();
    }

}