package com.entrevistador.orquestador.dominio.service;

import com.entrevistador.orquestador.dominio.port.EntrevistaDao;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class CrearEntrevistaService {

    private final EntrevistaDao entrevistaDao;

    public Mono<String> ejecutar() {
        return this.entrevistaDao.crearEntrevista();
    }

}
