package com.entrevistador.orquestador.infrastructure.adapter.dao;

import com.entrevistador.orquestador.dominio.model.ProcesoEntrevista;
import com.entrevistador.orquestador.dominio.port.ProcesoEntrevistaDao;
import com.entrevistador.orquestador.infrastructure.adapter.entity.ProcesoEntrevistaEntity;
import com.entrevistador.orquestador.infrastructure.adapter.mapper.ProcesoEntrevistaMapper;
import com.entrevistador.orquestador.infrastructure.adapter.repository.ProcesoEntrevistaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class ProcesoEntrevistaBdDao implements ProcesoEntrevistaDao {

    private final ProcesoEntrevistaRepository procesoEntrevistaRepository;
    private final ProcesoEntrevistaMapper     mapper;

    @Override
    public Mono<ProcesoEntrevista> crearEvento() {
        return this.procesoEntrevistaRepository.save(ProcesoEntrevistaEntity.builder().build())
                .map(this.mapper::mapProcesoEntrevistaEntityToProcesoEntrevista);
    }

    @Override
    public Mono<ProcesoEntrevista> obtenerEventoPorId(String idEvento) {
        return this.procesoEntrevistaRepository.findById(idEvento)
                .map(this.mapper::mapProcesoEntrevistaEntityToProcesoEntrevista);
    }

    @Override
    public Mono<ProcesoEntrevista> actualizar(ProcesoEntrevista procesoEntrevistaDto) {
        return Mono.just(this.mapper.mapProcesoEntrevistaToProcesoEntrevistaEntity(procesoEntrevistaDto))
                .flatMap(this.procesoEntrevistaRepository::save)
                .map(this.mapper::mapProcesoEntrevistaEntityToProcesoEntrevista);
    }
}
