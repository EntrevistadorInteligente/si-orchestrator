package com.entrevistador.orquestador.dominio.port;

import com.entrevistador.orquestador.dominio.model.Entrevista;
import com.entrevistador.orquestador.dominio.model.dto.EstadoEntrevistaDto;
import com.entrevistador.orquestador.dominio.model.dto.FormularioDto;
import com.entrevistador.orquestador.dominio.model.dto.RagsIdsDto;
import com.entrevistador.orquestador.dominio.model.enums.EstadoEntrevistaEnum;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EntrevistaDao {

    Mono<String> crearEntrevistaBase(String idHojaDeVidaRag, String username, FormularioDto formulario);

    Mono<Void> actualizarEntrevista(Entrevista entrevista);

    Mono<RagsIdsDto> consultarRagsId(String idEntrevista);

    Mono<Void> actualizarEstadoHojaDeVida(String idEntrevista, boolean esEntrevistaValida);

    Mono<Void> actualizarIdInformacionEmpresaRag(String idEntrevista, String idInformacionEmpresaRag);

    Mono<EstadoEntrevistaDto> obtenerEstadoEntrevistaPorUsuario(String username);

    Mono<EstadoEntrevistaDto> obtenerEstadoEntrevistaPorId(String id);

    Mono<Void> actualizarEstadoEntrevista(String idEntrevista, EstadoEntrevistaEnum estadoEntrevistaEnum);

    Flux<Entrevista> consultarUltimasEntrevistas(String username);
}
