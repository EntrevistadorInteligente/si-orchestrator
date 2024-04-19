package com.entrevistador.orquestador.infrastructure.adapter.repository;

import com.entrevistador.orquestador.dominio.model.dto.RagsIdsDto;
import com.entrevistador.orquestador.infrastructure.adapter.entity.EntrevistaEntity;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface EntrevistaRepository extends ReactiveMongoRepository<EntrevistaEntity, String> {

    @Query(value="{ '_id' : ?0 }", fields="{ 'idHojaDeVidaRag' : 1, 'idInformacionEmpresaRag' : 1, 'hojaDeVidaValida' : 1}")
    Mono<RagsIdsDto> obtenerRagsYEstadoEntrevistaPorId(String id);

}
