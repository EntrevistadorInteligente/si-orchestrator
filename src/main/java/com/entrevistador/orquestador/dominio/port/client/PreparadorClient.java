package com.entrevistador.orquestador.dominio.port.client;

import reactor.core.publisher.Mono;

public interface PreparadorClient {

    Mono<Void> generarEntrevista();

}
