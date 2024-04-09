package com.entrevistador.orquestador.infrastructure.adapter.repository;

import com.entrevistador.orquestador.infrastructure.adapter.entity.HojaDeVidaEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface HojaDeVidaRepository extends ReactiveMongoRepository<HojaDeVidaEntity, String> {
    Mono<HojaDeVidaEntity> findByUsername(String username);
}
