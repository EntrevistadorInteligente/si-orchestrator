package com.entrevistador.orquestador.application.usescases;

import com.entrevistador.orquestador.dominio.model.dto.EstadoEntrevistaDto;
import com.entrevistador.orquestador.dominio.model.dto.FormularioDto;
import com.entrevistador.orquestador.dominio.model.dto.VistaPreviaEntrevistaDto;

import java.util.List;
import java.util.Map;

import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;

public interface SolicitudEntrevista {
    Mono<Void> generarSolicitudEntrevista(String username, FormularioDto formulario);

    Mono<EstadoEntrevistaDto> obtenerEstadoEntrevistaPorUsuario(String username);
}
