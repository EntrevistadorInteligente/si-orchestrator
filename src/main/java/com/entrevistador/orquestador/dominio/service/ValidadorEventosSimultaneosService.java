package com.entrevistador.orquestador.dominio.service;

import com.entrevistador.orquestador.dominio.excepciones.CvOInfoEmpresaException;
import reactor.core.publisher.Mono;

public class ValidadorEventosSimultaneosService {

    public Mono<Boolean> ejecutar(String campoDeEventoAValidar) {
        if (campoDeEventoAValidar.equals("")) {
            return Mono.error(new CvOInfoEmpresaException("Hoja de vida o informacion empresa fue nulo"));
        }
        return Mono.just(campoDeEventoAValidar == null);
    }

}
