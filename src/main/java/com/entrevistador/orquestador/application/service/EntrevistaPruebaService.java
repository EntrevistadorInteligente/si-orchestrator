package com.entrevistador.orquestador.application.service;

import com.entrevistador.orquestador.application.usescases.EntrevistaPrueba;
import com.entrevistador.orquestador.dominio.model.dto.IdEntrevistaDto;
import com.entrevistador.orquestador.dominio.model.dto.SoloPerfilImp;
import com.entrevistador.orquestador.dominio.port.EntrevistaPruebaDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class EntrevistaPruebaService implements EntrevistaPrueba {

    private final EntrevistaPruebaDao entrevistaPruebaDao;

    @Override
    public Flux<SoloPerfilImp> getPerfiles(int limit) {
        return this.entrevistaPruebaDao.getPerfil(limit);
    }

    @Override
    public Mono<IdEntrevistaDto> getIdEntrevista(String perfil) {
        return this.entrevistaPruebaDao.getIdEntrevistaByPerfil(perfil);
    }

}
