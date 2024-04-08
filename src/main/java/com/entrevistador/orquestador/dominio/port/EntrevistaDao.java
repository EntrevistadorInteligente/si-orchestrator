package com.entrevistador.orquestador.dominio.port;

import com.entrevistador.orquestador.dominio.model.Entrevista;
import com.entrevistador.orquestador.dominio.model.dto.RagsIdsDto;
import reactor.core.publisher.Mono;

public interface EntrevistaDao {

    Mono<String> crearEntrevista();

    Mono<String> actualizarEntrevista(Entrevista entrevista);

    Mono<RagsIdsDto> consultarRagsId(String idEntrevista);

}
