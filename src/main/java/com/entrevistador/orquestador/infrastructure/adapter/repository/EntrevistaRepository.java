package com.entrevistador.orquestador.infrastructure.adapter.repository;

import com.entrevistador.orquestador.infrastructure.adapter.entity.EntrevistaEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntrevistaRepository extends ReactiveMongoRepository<EntrevistaEntity, String> {
}
