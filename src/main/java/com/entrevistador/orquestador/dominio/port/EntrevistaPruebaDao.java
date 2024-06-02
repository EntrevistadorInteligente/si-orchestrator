package com.entrevistador.orquestador.dominio.port;

import com.entrevistador.orquestador.dominio.model.dto.IdEntrevistaDto;
import com.entrevistador.orquestador.dominio.model.dto.SoloPerfilImp;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EntrevistaPruebaDao {
    Flux<SoloPerfilImp> getPerfil();

    Mono<IdEntrevistaDto> getIdEntrevistaByPerfil(String perfil);
}
