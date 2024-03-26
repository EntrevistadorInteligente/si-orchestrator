package com.entrevistador.orquestador.dominio.port;

import com.entrevistador.orquestador.dominio.model.dto.ProcesoEntrevistaDto;
import com.entrevistador.orquestador.infrastructure.adapter.entity.ProcesoEntrevistaEntity;
import reactor.core.publisher.Mono;

public interface ProcesoEntrevistaDao {

    Mono<ProcesoEntrevistaDto> crearEvento();

    Mono<ProcesoEntrevistaDto> obtenerEventoPorId(String idEvento);

    Mono<ProcesoEntrevistaDto> actualizar(ProcesoEntrevistaEntity procesoEntrevistaEntity);

}
