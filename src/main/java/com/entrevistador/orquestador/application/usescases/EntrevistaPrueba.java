package com.entrevistador.orquestador.application.usescases;

import com.entrevistador.orquestador.dominio.model.dto.IdEntrevistaDto;
import com.entrevistador.orquestador.dominio.model.dto.SoloPerfilDto;
import com.entrevistador.orquestador.dominio.model.dto.SoloPerfilImp;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EntrevistaPrueba {
    Flux<SoloPerfilImp> getPerfiles(int limit);

    Mono<IdEntrevistaDto> getIdEntrevista(String perfil);
}
