package com.entrevistador.orquestador.infrastructure.adapter.dao;

import com.entrevistador.orquestador.dominio.model.IdEntrevista;
import com.entrevistador.orquestador.dominio.model.SoloPerfil;
import com.entrevistador.orquestador.dominio.port.EntrevistaPruebaDao;
import com.entrevistador.orquestador.infrastructure.adapter.mapper.EntrevistaPruebaMapper;
import com.entrevistador.orquestador.infrastructure.adapter.repository.EntrevistaRepository;
import com.entrevistador.orquestador.infrastructure.properties.EntrevistaPruebaConst;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class EntrevistaPruebaBdDao implements EntrevistaPruebaDao {

    private final EntrevistaRepository entrevistaRepository;
    private final EntrevistaPruebaConst  entrevistaPruebaConst;
    private final EntrevistaPruebaMapper mapper;

    @Override
    public Flux<SoloPerfil> getPerfil() {
        return this.entrevistaRepository.obtenerPerfilEmpresa(entrevistaPruebaConst.getLimitperfiles())
                .map(this.mapper::mapEntrevistaEntityToSoloPerfil);
    }

    @Override
    public Mono<IdEntrevista> getIdEntrevistaByPerfil(String perfil) {
        return this.entrevistaRepository.obtenerIdEntrevistaPorPerfil(perfil)
                .map(this.mapper::mapEntrevistaEntityToIdEntrevista);
    }
}
