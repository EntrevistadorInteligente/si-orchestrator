package com.entrevistador.orquestador.infrastructure.adapter.dao;

import com.entrevistador.orquestador.dominio.excepciones.IdNoEncontradoException;
import com.entrevistador.orquestador.dominio.model.Entrevista;
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
    public Mono<String> crearEntrevistaBase(String idHojaDeVidaRag) {
        return this.entrevistaRepository.save(EntrevistaEntity.builder()
                        .idHojaDeVidaRag(idHojaDeVidaRag)
                        .build())
                .map(EntrevistaEntity::getUuid);
    }

    @Override
    public Mono<Void> actualizarEntrevista(Entrevista entrevista) {
        return entrevistaRepository.findById(entrevista.getUuid())
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
                                            .preguntas(entrevista.getInformacionEmpresaDto().getPreguntas())
                                            .isHojaDeVidaValida(entrevistaEntity.isHojaDeVidaValida())
                                            .build()).then());

    }

    @Override
    public Mono<RagsIdsDto> consultarRagsId(String idEntrevista) {
        return entrevistaRepository.findIdHojaDeVidaAndInformacionEmpresaAndEsHojaDeVidaValidaIdById(idEntrevista);
    }

    @Override
    public Mono<Void> actualizarEstadoEntrevista(String idEntrevista, boolean esEntrevistaValida) {
        return entrevistaRepository.findById(idEntrevista)
                .flatMap(entrevista -> {
                    entrevista.setHojaDeVidaValida(esEntrevistaValida);
                    return entrevistaRepository.save(entrevista);
                })
                .then();
    }

}
