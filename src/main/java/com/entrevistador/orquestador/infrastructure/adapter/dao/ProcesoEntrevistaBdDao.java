package com.entrevistador.orquestador.infrastructure.adapter.dao;

import com.entrevistador.orquestador.dominio.model.dto.ProcesoEntrevistaDto;
import com.entrevistador.orquestador.dominio.port.ProcesoEntrevistaDao;
import com.entrevistador.orquestador.infrastructure.adapter.entity.ProcesoEntrevistaEntity;
import com.entrevistador.orquestador.infrastructure.adapter.repository.ProcesoEntrevistaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Date;

@Repository
@RequiredArgsConstructor
public class ProcesoEntrevistaBdDao implements ProcesoEntrevistaDao {
    private final ProcesoEntrevistaRepository procesoEntrevistaRepository;

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
    public Mono<ProcesoEntrevistaDto> actualizar(ProcesoEntrevistaDto procesoEntrevistaDto) {
        return Mono.just(ProcesoEntrevistaEntity.builder()
                        .uuid(procesoEntrevistaDto.getUuid())
                        .fechaHora(procesoEntrevistaDto.getFechaYHora())
                        .estado(procesoEntrevistaDto.getEstado())
                        .fuente(procesoEntrevistaDto.getFuente())
                        .error(procesoEntrevistaDto.getError())
                        .build())
                .flatMap(this.procesoEntrevistaRepository::save)
                .map(procesoEntrevistaEntity ->
                        ProcesoEntrevistaDto.builder()
                                .uuid(procesoEntrevistaEntity.getUuid())
                                .fechaYHora(procesoEntrevistaEntity.getFechaHora())
                                .fuente(procesoEntrevistaEntity.getFuente())
                                .estado(procesoEntrevistaEntity.getEstado())
                                .error(procesoEntrevistaEntity.getError())
                                .build()
                );
    }
}
