package com.entrevistador.orquestador.dominio.port;

import reactor.core.publisher.Mono;

public interface EntrevistaDao {

    Mono<String> crearEntrevista();

}
