package com.entrevistador.orquestador.dominio.port;

import com.entrevistador.orquestador.dominio.model.ProcesoEntrevista;
import reactor.core.publisher.Mono;

public interface ProcesoEntrevistaDao {

    Mono<ProcesoEntrevista> crearEvento();

    Mono<ProcesoEntrevista> obtenerEventoPorId(String idEvento);

    Mono<ProcesoEntrevista> actualizar(ProcesoEntrevista procesoEntrevistaDto);

}
