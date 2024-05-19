package com.entrevistador.orquestador.application.usescases;

import com.entrevistador.orquestador.dominio.model.dto.EstadoEntrevistaDto;
import com.entrevistador.orquestador.dominio.model.dto.FormularioDto;
import reactor.core.publisher.Mono;

public interface SolicitudEntrevista {
    Mono<Void> generarSolicitudEntrevista(String username, FormularioDto formulario);

    Mono<EstadoEntrevistaDto> obtenerEstadoEntrevistaPorUsuario(String username);
}
