package com.entrevistador.orquestador.dominio.service;

import com.entrevistador.orquestador.dominio.excepciones.IdNoEncontradoException;
import com.entrevistador.orquestador.dominio.model.ProcesoEntrevista;
import com.entrevistador.orquestador.dominio.port.ProcesoEntrevistaDao;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
public class ActualizarEstadoProcesoEntrevistaService {
    private final ProcesoEntrevistaDao procesoEntrevistaDao;

    public Mono<Void> ejecutar(ProcesoEntrevista procesoEntrevista) {
        return this.procesoEntrevistaDao
                .obtenerEventoPorId(procesoEntrevista.getUuid())
                .switchIfEmpty(Mono.error(new IdNoEncontradoException("Id de estado no encontrado. ID: " + procesoEntrevista.getUuid())))
                .doOnNext(procesoEntrevistaActual -> procesoEntrevistaActual.actualizar(procesoEntrevista))
                .flatMap(this.procesoEntrevistaDao::actualizar)
                .then();
    }
}