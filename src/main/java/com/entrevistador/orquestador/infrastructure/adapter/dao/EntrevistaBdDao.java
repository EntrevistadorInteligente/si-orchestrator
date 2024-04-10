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
    private final EntrevistaRepository entrevistaRepository;

    @Override
    public Mono<String> crearEntrevista() {
        return this.entrevistaRepository.save(EntrevistaEntity.builder().build())
                .map(EntrevistaEntity::getUuid);
    }

    @Override
    public Mono<String> actualizarEntrevista(Entrevista entrevista) {
        final String uuidError = "Id de estado no encontrado. ID: %s";
        return entrevistaRepository.findById(entrevista.getUuid())
                .switchIfEmpty(Mono.error(new IdNoEncontradoException(String.format(uuidError, entrevista.getUuid()))))
                .flatMap(entrevistaEntity -> {
                        entrevistaRepository.save(EntrevistaEntity.builder()
                                            .uuid(entrevistaEntity.getUuid())
                                            .idHojaDeVidaRag(entrevista.getIdHojaDeVidaRag())
                                            .idInformacionEmpresaRag(entrevista.getInformacionEmpresaDto().getIdInformacionEmpresaRag())
                                            .empresa(entrevista.getInformacionEmpresaDto().getEmpresa())
                                            .perfilEmpresa(entrevista.getInformacionEmpresaDto().getPerfil())
                                            .seniorityEmpresa(entrevista.getInformacionEmpresaDto().getSeniority())
                                            .pais(entrevista.getInformacionEmpresaDto().getPais())
                                            .preguntas(entrevista.getInformacionEmpresaDto().getPreguntas())
                                            .build());
                        return Mono.just("");
                    }
                );
    }

    @Override
    public Mono<RagsIdsDto> consultarRagsId(String idEntrevista) {
        return entrevistaRepository.findIdHojaDeVidaAndInformacionEmpresaIdById(idEntrevista);
    }
}
