package com.entrevistador.orquestador.infrastructure.adapter.dao;

import com.entrevistador.orquestador.dominio.model.dto.ProcesoEntrevistaDto;
import com.entrevistador.orquestador.dominio.port.ProcesoEntrevistaDao;
import com.entrevistador.orquestador.infrastructure.adapter.entity.ProcesoEntrevistaEntity;
import com.entrevistador.orquestador.infrastructure.adapter.repository.ProcesoEntrevistaRepository;
import java.util.Date;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class ProcesoEntrevistaBdDao implements ProcesoEntrevistaDao {

    private final ProcesoEntrevistaRepository procesoEntrevistaRepository;

    public ProcesoEntrevistaBdDao(ProcesoEntrevistaRepository procesoEntrevistaRepository) {
        this.procesoEntrevistaRepository = procesoEntrevistaRepository;
    }

    @Override
    public Mono<ProcesoEntrevistaDto> crearEvento() {
        ProcesoEntrevistaEntity procesoEntrevistaEntity = procesoEntrevistaRepository.save(ProcesoEntrevistaEntity.builder().build());
        return Mono.just(ProcesoEntrevistaDto.builder()
                .uuid(procesoEntrevistaEntity.getProcesoEntrevistaId())
                .fechaYHora(new Date())
                .estado(procesoEntrevistaEntity.getEstado())
                .fuente(procesoEntrevistaEntity.getFuente())
                .error(procesoEntrevistaEntity.getError())
                .build());
    }

    @Override
    public ProcesoEntrevistaDto obtenerEventoPorId(String idEvento) {
        Optional<ProcesoEntrevistaEntity> procesoEntrevistaEntity = procesoEntrevistaRepository.findById(idEvento);
        return procesoEntrevistaEntity.map(entrevistaEntity -> ProcesoEntrevistaDto.builder()
                .uuid(entrevistaEntity.getProcesoEntrevistaId())
                .fechaYHora(entrevistaEntity.getFechaHora())
                .fuente(entrevistaEntity.getFuente())
                .estado(entrevistaEntity.getEstado())
                .error(entrevistaEntity.getError())
                .build()).orElse(null);
    }

    @Override
    public ProcesoEntrevistaDto actualizar(ProcesoEntrevistaEntity procesoEntrevistaEntity){
        ProcesoEntrevistaEntity procesoEntrevistaEntity1 = procesoEntrevistaRepository.save(procesoEntrevistaEntity);
        return ProcesoEntrevistaDto.builder()
                .uuid(procesoEntrevistaEntity1.getProcesoEntrevistaId())
                .fechaYHora(procesoEntrevistaEntity1.getFechaHora())
                .fuente(procesoEntrevistaEntity1.getFuente())
                .estado(procesoEntrevistaEntity1.getEstado())
                .error(procesoEntrevistaEntity1.getError())
                .build();
    }

}
