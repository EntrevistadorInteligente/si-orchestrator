package com.entrevistador.orquestador.infrastructure.adapter.dao;

import com.entrevistador.orquestador.dominio.port.EntrevistaDao;
import com.entrevistador.orquestador.infrastructure.adapter.entity.EntrevistaEntity;
import com.entrevistador.orquestador.infrastructure.adapter.repository.EntrevistaRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class EntrevistaBdDao implements EntrevistaDao {

    private final EntrevistaRepository entrevistaRepository;

    public EntrevistaBdDao(EntrevistaRepository entrevistaRepository) {
        this.entrevistaRepository = entrevistaRepository;
    }

    @Override
    public Mono<String> crearEntrevista() {
        //TODO: Cambiar a una DBMS Reactivo
        /*return Mono.just(this.entrevistaRepository.save(new EntrevistaEntity()))
                .map(EntrevistaEntity::getUuid);*/

        return Mono.just("9d2bf204-8f5f-48a1-beff-b83b9db49855");
    }

}
