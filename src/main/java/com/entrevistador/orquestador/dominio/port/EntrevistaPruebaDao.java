package com.entrevistador.orquestador.dominio.port;

import com.entrevistador.orquestador.dominio.model.dto.SoloPerfilDto;
import com.entrevistador.orquestador.dominio.model.dto.SoloPerfilImp;
import reactor.core.publisher.Flux;

public interface EntrevistaPruebaDao {
    Flux<SoloPerfilImp> getPerfil(int limit);
}
