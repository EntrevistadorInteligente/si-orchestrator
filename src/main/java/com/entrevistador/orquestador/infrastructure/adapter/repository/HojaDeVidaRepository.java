package com.entrevistador.orquestador.infrastructure.adapter.repository;

import com.entrevistador.orquestador.infrastructure.adapter.entity.HojaDeVidaEntity;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface HojaDeVidaRepository extends ReactiveMongoRepository<HojaDeVidaEntity, String> {
    Mono<HojaDeVidaEntity> findFirstByUsernameOrderByFechaCreacionDesc(String username);

    Mono<HojaDeVidaEntity> findFirstByUsernameAndEstadoHojaDeVida(String username, String estadoHojaDeVida);

    @Query("{ 'username' : ?0, 'estadoHojaDeVida' : { $ne: 'AT' } }")
    @Update("{'$set': {'estadoHojaDeVida': ?1 }}")
    Mono<Void> actualizarEstadoHojadeVidaPorUsername(String username, String estadoHojaDeVida);
}
