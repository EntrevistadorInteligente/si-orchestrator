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
        return this.entrevistaRepository.save(EntrevistaEntity.builder().build())
                .map(EntrevistaEntity::getUuid);
    }
}
