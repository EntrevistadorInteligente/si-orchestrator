package com.entrevistador.orquestador.infrastructure.adapter.dao;

import com.entrevistador.orquestador.dominio.excepciones.IdNoEncontradoException;
import com.entrevistador.orquestador.dominio.model.Entrevista;
import com.entrevistador.orquestador.dominio.model.EstadoEntrevista;
import com.entrevistador.orquestador.dominio.model.Formulario;
import com.entrevistador.orquestador.dominio.model.enums.EstadoEntrevistaEnum;
import com.entrevistador.orquestador.dominio.port.EntrevistaDao;
import com.entrevistador.orquestador.infrastructure.adapter.dto.RagsIdsDto;
import com.entrevistador.orquestador.infrastructure.adapter.entity.EntrevistaEntity;
import com.entrevistador.orquestador.infrastructure.adapter.mapper.EntrevistaMapper;
import com.entrevistador.orquestador.infrastructure.adapter.repository.EntrevistaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class EntrevistaBdDao implements EntrevistaDao {
    public static final String ID_DE_ESTADO_NO_ENCONTRADO_ID = "Id de estado no encontrado. ID: %s";
    private final EntrevistaRepository entrevistaRepository;
    private final EntrevistaMapper     mapper;

    @Override
    public Mono<String> crearEntrevistaBase(String idHojaDeVidaRag, String username, Formulario formulario) {
        return Mono.just(this.mapper.mapFormularioToEntrevistaEntity(idHojaDeVidaRag, username, formulario))
                .flatMap(this.entrevistaRepository::save)
                .map(EntrevistaEntity::getUuid);
    }

    @Override
    public Mono<Void> actualizarEntrevista(Entrevista entrevista) {
        return this.entrevistaRepository.findById(entrevista.getUuid())
                .switchIfEmpty(Mono.error(new IdNoEncontradoException(String.format(ID_DE_ESTADO_NO_ENCONTRADO_ID, entrevista.getUuid()))))
                .map(entrevistaEntity -> this.mapper.mapEntrevistaToEntrevistaEntity(entrevistaEntity, entrevista))
                .flatMap(this.entrevistaRepository::save)
                .then();
    }

    @Override
    public Mono<RagsIdsDto> consultarRagsId(String idEntrevista) {
        return this.entrevistaRepository.obtenerRagsYEstadoEntrevistaPorId(idEntrevista);
    }

    @Override
    public Mono<Void> actualizarEstadoHojaDeVida(String idEntrevista, boolean esEntrevistaValida) {
        return this.entrevistaRepository.findById(idEntrevista)
                .flatMap(entrevista -> {
                    entrevista.setHojaDeVidaValida(true);
                    return entrevistaRepository.save(entrevista);
                })
                .then();
    }


    @Override
    public Mono<Void> actualizarIdInformacionEmpresaRag(String idEntrevista, String idInformacionEmpresaRag) {
        return this.entrevistaRepository.findById(idEntrevista)
                .flatMap(entrevista -> {
                    entrevista.setIdInformacionEmpresaRag(idInformacionEmpresaRag);
                    return entrevistaRepository.save(entrevista);
                })
                .then();
    }

    @Override
    public Mono<EstadoEntrevista> obtenerEstadoEntrevistaPorUsuario(String username) {
        return this.entrevistaRepository.obtenerEntrevistaEnProcesoPorUsuario(username)
                .map(this.mapper::mapEntrevistaEntityToEstadoEntrevista);
    }

    @Override
    public Mono<EstadoEntrevista> obtenerEstadoEntrevistaPorId(String id) {
       return this.entrevistaRepository.findById(id)
               .map(this.mapper::mapEntrevistaEntityToEstadoEntrevista);
    }

    @Override
    public Mono<Void> actualizarEstadoEntrevista(String idEntrevista, EstadoEntrevistaEnum estadoEntrevistaEnum) {
        return this.entrevistaRepository.actualizarEstadoEntrevistaPorId(idEntrevista, estadoEntrevistaEnum.name());
    }

    @Override
    public Flux<Entrevista> consultarUltimasEntrevistas(String username) {
        return this.entrevistaRepository.findByUsernameOrderByFechaCreacionDesc(username)
                .map(this.mapper::mapOutEntrevistaEntityToEntrevista);
    }

    @Override
    public Mono<Void> terminarEntrevista(String id, String feedbackUsuario) {
        return this.entrevistaRepository.findById(id)
                .flatMap(entrevista -> {
                    entrevista.setEstadoEntrevista(EstadoEntrevistaEnum.FN.name());
                    entrevista.setFeedbackUsuario(feedbackUsuario);
                    return entrevistaRepository.save(entrevista);
                })
                .then();
    }

}
