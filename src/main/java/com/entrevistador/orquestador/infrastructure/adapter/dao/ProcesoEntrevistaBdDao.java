package com.entrevistador.orquestador.infrastructure.adapter.dao;

import com.entrevistador.orquestador.dominio.model.dto.ProcesoEntrevistaDto;
import com.entrevistador.orquestador.dominio.port.ProcesoEntrevistaDao;
import com.entrevistador.orquestador.infrastructure.adapter.entity.ProcesoEntrevistaEntity;
import com.entrevistador.orquestador.infrastructure.adapter.repository.ProcesoEntrevistaRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Date;

@Repository
public class ProcesoEntrevistaBdDao implements ProcesoEntrevistaDao {
    private final ProcesoEntrevistaRepository procesoEntrevistaRepository;

    public ProcesoEntrevistaBdDao(ProcesoEntrevistaRepository procesoEntrevistaRepository) {
        this.procesoEntrevistaRepository = procesoEntrevistaRepository;
    }

    @Override
    public Mono<ProcesoEntrevistaDto> crearEvento() {
        return this.procesoEntrevistaRepository.save(ProcesoEntrevistaEntity.builder()
                        .build())
                .map(procesoEntrevistaEntity -> ProcesoEntrevistaDto.builder()
                        .uuid(procesoEntrevistaEntity.getUuid())
                        .fechaYHora(new Date())
                        .estado(procesoEntrevistaEntity.getEstado())
                        .fuente(procesoEntrevistaEntity.getFuente())
                        .error(procesoEntrevistaEntity.getError())
                        .build());
    }

    @Override
    public Mono<ProcesoEntrevistaDto> obtenerEventoPorId(String idEvento) {
        return this.procesoEntrevistaRepository.findById(idEvento)
                .map(procesoEntrevistaEntity ->
                        ProcesoEntrevistaDto.builder()
                                .uuid(procesoEntrevistaEntity.getUuid())
                                .fechaYHora(procesoEntrevistaEntity.getFechaHora())
                                .fuente(procesoEntrevistaEntity.getFuente())
                                .estado(procesoEntrevistaEntity.getEstado())
                                .error(procesoEntrevistaEntity.getError())
                                .build());
    }

    @Override
    public Mono<ProcesoEntrevistaDto> actualizar(ProcesoEntrevistaEntity procesoEntrevistaEntity) {
        return this.procesoEntrevistaRepository.save(procesoEntrevistaEntity)
                .map(procesoEntrevistaEntity1 ->
                        ProcesoEntrevistaDto.builder()
                                .uuid(procesoEntrevistaEntity1.getUuid())
                                .fechaYHora(procesoEntrevistaEntity1.getFechaHora())
                                .fuente(procesoEntrevistaEntity1.getFuente())
                                .estado(procesoEntrevistaEntity1.getEstado())
                                .error(procesoEntrevistaEntity1.getError())
                                .build()
                );
    }
}
