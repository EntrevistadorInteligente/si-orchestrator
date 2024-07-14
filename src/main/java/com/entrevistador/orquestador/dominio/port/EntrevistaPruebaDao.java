package com.entrevistador.orquestador.dominio.port;

import com.entrevistador.orquestador.dominio.model.IdEntrevista;
import com.entrevistador.orquestador.dominio.model.SoloPerfil;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EntrevistaPruebaDao {
    Flux<SoloPerfil> getPerfil();

    Mono<IdEntrevista> getIdEntrevistaByPerfil(String perfil);
}
