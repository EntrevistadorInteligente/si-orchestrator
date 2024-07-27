package com.entrevistador.orquestador.application.usescases;

import com.entrevistador.orquestador.dominio.model.EntrevistaUsuario;
import com.entrevistador.orquestador.dominio.model.EstadoEntrevista;
import com.entrevistador.orquestador.dominio.model.Formulario;
import reactor.core.publisher.Mono;

public interface SolicitudEntrevista {
    Mono<Void> generarSolicitudEntrevista(String username, Formulario formulario);

    Mono<EstadoEntrevista> obtenerEstadoEntrevistaPorUsuario(String username);

    Mono<EstadoEntrevista> obtenerEstadoEntrevistaPorId(String id);

    Mono<Void> terminarEntrevista(String id, String feedbackUsuario);

    Mono<EntrevistaUsuario> obtenerEntrevistaPorId(String id);
}
