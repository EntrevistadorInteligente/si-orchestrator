package com.entrevistador.orquestador.dominio.port;

import com.entrevistador.orquestador.dominio.model.Entrevista;
import com.entrevistador.orquestador.dominio.model.dto.RagsIdsDto;
import reactor.core.publisher.Mono;

public interface EntrevistaDao {

    Mono<String> crearEntrevistaBase(String idHojaDeVidaRag);

    Mono<Void> actualizarEntrevista(Entrevista entrevista);

    Mono<RagsIdsDto> consultarRagsId(String idEntrevista);
    Mono<Void> actualizarEstadoEntrevista(String idEntrevista, boolean esEntrevistaValida);

}
