package com.entrevistador.orquestador.infrastructure.adapter.dao;

import com.entrevistador.orquestador.dominio.excepciones.IdNoEncontradoException;
import com.entrevistador.orquestador.dominio.model.Entrevista;
import com.entrevistador.orquestador.dominio.model.dto.EstadoEntrevistaDto;
import com.entrevistador.orquestador.dominio.model.dto.FormularioDto;
import com.entrevistador.orquestador.dominio.model.dto.RagsIdsDto;
import com.entrevistador.orquestador.dominio.port.EntrevistaDao;
import com.entrevistador.orquestador.infrastructure.adapter.entity.EntrevistaEntity;
import com.entrevistador.orquestador.infrastructure.adapter.repository.EntrevistaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class EntrevistaBdDao implements EntrevistaDao {
    public static final String ID_DE_ESTADO_NO_ENCONTRADO_ID = "Id de estado no encontrado. ID: %s";
    private final EntrevistaRepository entrevistaRepository;

    @Override
    public Mono<String> crearEntrevistaBase(String idHojaDeVidaRag, String username, FormularioDto formulario) {
        return this.entrevistaRepository.save(EntrevistaEntity.builder()
                        .idHojaDeVidaRag(idHojaDeVidaRag)
                        .username(username)
                        .empresa(formulario.getEmpresa())
                        .pais(formulario.getPais())
                        .seniorityEmpresa(formulario.getSeniority())
                        .perfilEmpresa(formulario.getPerfil())
                        .descripcionVacante(formulario.getDescripcionVacante())
                        .build())
                .map(EntrevistaEntity::getUuid);
    }

    @Override
    public Mono<Void> actualizarEntrevista(Entrevista entrevista) {
        return this.entrevistaRepository.findById(entrevista.getUuid())
                .switchIfEmpty(Mono.error(new IdNoEncontradoException(String.format(ID_DE_ESTADO_NO_ENCONTRADO_ID, entrevista.getUuid()))))
                .flatMap(entrevistaEntity ->
                        entrevistaRepository.save(EntrevistaEntity.builder()
                                            .uuid(entrevistaEntity.getUuid())
                                            .idHojaDeVidaRag(entrevistaEntity.getIdHojaDeVidaRag())
                                            .idInformacionEmpresaRag(entrevista.getInformacionEmpresaDto().getIdInformacionEmpresaRag())
                                            .empresa(entrevista.getInformacionEmpresaDto().getEmpresa())
                                            .perfilEmpresa(entrevista.getInformacionEmpresaDto().getPerfil())
                                            .seniorityEmpresa(entrevista.getInformacionEmpresaDto().getSeniority())
                                            .pais(entrevista.getInformacionEmpresaDto().getPais())
                                            .hojaDeVidaValida(entrevistaEntity.isHojaDeVidaValida())
                                            .build()).then());

    }

    @Override
    public Mono<RagsIdsDto> consultarRagsId(String idEntrevista) {
        return this.entrevistaRepository.obtenerRagsYEstadoEntrevistaPorId(idEntrevista);
    }

    @Override
    public Mono<Void> actualizarEstadoEntrevista(String idEntrevista, boolean esEntrevistaValida) {
        return this.entrevistaRepository.findById(idEntrevista)
                .flatMap(entrevista -> {
                    entrevista.setHojaDeVidaValida(esEntrevistaValida);
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
    public Mono<EstadoEntrevistaDto> obtenerEstadoEntrevistaPorUsuario(String username) {
        return this.entrevistaRepository.findByUsername(username).map( ent -> new EstadoEntrevistaDto(ent.getUuid(), ent.getEstadoEntrevista()));
    }

}
