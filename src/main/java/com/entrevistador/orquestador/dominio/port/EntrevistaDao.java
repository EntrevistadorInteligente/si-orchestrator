package com.entrevistador.orquestador.dominio.port;

import com.entrevistador.orquestador.dominio.model.Entrevista;
import com.entrevistador.orquestador.dominio.model.dto.EstadoEntrevistaDto;
import com.entrevistador.orquestador.dominio.model.dto.FormularioDto;
import com.entrevistador.orquestador.dominio.model.dto.RagsIdsDto;
import reactor.core.publisher.Mono;

public interface EntrevistaDao {

    Mono<String> crearEntrevistaBase(String idHojaDeVidaRag, String username, FormularioDto formulario);

    Mono<Void> actualizarEntrevista(Entrevista entrevista);

    Mono<RagsIdsDto> consultarRagsId(String idEntrevista);

    Mono<Void> actualizarEstadoEntrevista(String idEntrevista, boolean esEntrevistaValida);

    Mono<Void> actualizarIdInformacionEmpresaRag(String idEntrevista, String idInformacionEmpresaRag);

    Mono<EstadoEntrevistaDto> obtenerEstadoEntrevistaPorUsuario(String username);
}
