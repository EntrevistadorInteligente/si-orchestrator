package com.entrevistador.orquestador.application.service;

import com.entrevistador.orquestador.application.usescases.EntrevistaPrueba;
import com.entrevistador.orquestador.dominio.model.IdEntrevista;
import com.entrevistador.orquestador.dominio.model.SoloPerfil;
import com.entrevistador.orquestador.dominio.port.EntrevistaPruebaDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class EntrevistaPruebaService implements EntrevistaPrueba {

    private final EntrevistaPruebaDao entrevistaPruebaDao;

    @Override
    public Flux<SoloPerfil> getPerfiles() {
        return this.entrevistaPruebaDao.getPerfil();
    }

    @Override
    @Transactional
    public Mono<IdEntrevista> getIdEntrevista(String perfil) {
        return this.entrevistaPruebaDao.getIdEntrevistaByPerfil(perfil);
    }
}
