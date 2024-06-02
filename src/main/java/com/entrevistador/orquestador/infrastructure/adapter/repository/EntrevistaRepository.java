package com.entrevistador.orquestador.infrastructure.adapter.repository;

import com.entrevistador.orquestador.application.dto.RagsIdsDto;
import com.entrevistador.orquestador.infrastructure.adapter.entity.EntrevistaEntity;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface EntrevistaRepository extends ReactiveMongoRepository<EntrevistaEntity, String> {

    @Query(value="{ '_id' : ?0 }", fields="{ 'idHojaDeVidaRag' : 1, 'idInformacionEmpresaRag' : 1, " +
            "'hojaDeVidaValida' : 1, 'username' : 1}")
    Mono<RagsIdsDto> obtenerRagsYEstadoEntrevistaPorId(String id);

    @Aggregation(pipeline = {"""
            {
                '$group': {
                    '_id' : '$perfilEmpresa',
                    count: { $sum: 1}
                    }
            }
            """,
            """
            {
                '$sort': { count: -1 }
            }
            """,
            """
            {
                '$limit': ?0
            }
            """,
            """
            {
                '$project' : {
                  '_id' : 0,
                  perfilEmpresa: '$_id'
                }
              }
            """
            })
    Flux<EntrevistaEntity> obtenerPerfilEmpresa(int limit);

    @Aggregation(pipeline ={
            "{'$match': { perfilEmpresa: ?0, estadoEntrevista: { $in: ['FN','FG'] }}}",
            "{'$limit': 1 }"
    })
    Mono<EntrevistaEntity> obtenerIdEntrevistaPorPerfil(String perfil);

    @Query(value="{ 'username' : ?0, 'estadoEntrevista' : { $ne: 'FN' } }")
    Mono<EntrevistaEntity> obtenerEntrevistaEnProcesoPorUsuario(String username);

    @Query("{ '_id' : ?0 }")
    @Update("{ '$set': { 'estadoEntrevista': ?1 } }")
    Mono<Void> actualizarEstadoEntrevistaPorId(String id, String estadoEntrevista);

    Flux<EntrevistaEntity> findByUsernameOrderByFechaCreacionDesc(String username);
}
