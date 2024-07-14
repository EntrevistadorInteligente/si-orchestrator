package com.entrevistador.orquestador.application.usescases;

import com.entrevistador.orquestador.dominio.model.IdEntrevista;
import com.entrevistador.orquestador.dominio.model.SoloPerfil;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EntrevistaPrueba {
    Flux<SoloPerfil> getPerfiles();

    Mono<IdEntrevista> getIdEntrevista(String perfil);
}
