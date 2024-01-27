package com.entrevistador.orquestador.dominio.port;

import com.entrevistador.orquestador.dominio.model.dto.ProcesoEntrevistaDto;
import reactor.core.publisher.Mono;

public interface ProcesoEntrevistaDao {

    Mono<ProcesoEntrevistaDto> crearEvento();

    ProcesoEntrevistaDto obtenerEventoPorId();

    ProcesoEntrevistaDto obtenerEventoPornombre();

}
