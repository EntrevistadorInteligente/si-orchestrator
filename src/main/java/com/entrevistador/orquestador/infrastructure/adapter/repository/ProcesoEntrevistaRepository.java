package com.entrevistador.orquestador.infrastructure.adapter.repository;

import com.entrevistador.orquestador.infrastructure.adapter.entity.ProcesoEntrevistaEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcesoEntrevistaRepository extends ReactiveMongoRepository<ProcesoEntrevistaEntity, String> {

}
