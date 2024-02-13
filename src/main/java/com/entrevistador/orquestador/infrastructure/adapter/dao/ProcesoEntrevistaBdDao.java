package com.entrevistador.orquestador.infrastructure.adapter.dao;

import com.entrevistador.orquestador.dominio.model.dto.ProcesoEntrevistaDto;
import com.entrevistador.orquestador.dominio.port.ProcesoEntrevistaDao;
import com.entrevistador.orquestador.infrastructure.adapter.entity.ProcesoEntrevistaEntity;
import com.entrevistador.orquestador.infrastructure.adapter.repository.ProcesoEntrevistaRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Repository
public class ProcesoEntrevistaBdDao implements ProcesoEntrevistaDao {

    private final ProcesoEntrevistaRepository procesoEntrevistaRepository;

    public ProcesoEntrevistaBdDao(ProcesoEntrevistaRepository procesoEntrevistaRepository) {
        this.procesoEntrevistaRepository = procesoEntrevistaRepository;
    }

    @Override
    public Mono<ProcesoEntrevistaDto> crearEvento() {
        ProcesoEntrevistaEntity procesoEntrevistaEntity = procesoEntrevistaRepository.save(new ProcesoEntrevistaEntity());
        return Mono.just(ProcesoEntrevistaDto.builder()
                .uuid(procesoEntrevistaEntity.getProcesoEntrevistaId())
                .fechaYHora(LocalDate.now().toString())
                .estado(procesoEntrevistaEntity.getEstado())
                .fuente(procesoEntrevistaEntity.getFuente())
                .error(procesoEntrevistaEntity.getError())
                .build());
    }

    @Override
    public ProcesoEntrevistaDto obtenerEventoPorId() {
        return null;
    }

    @Override
    public ProcesoEntrevistaDto obtenerEventoPornombre() {
        return null;
    }


}
