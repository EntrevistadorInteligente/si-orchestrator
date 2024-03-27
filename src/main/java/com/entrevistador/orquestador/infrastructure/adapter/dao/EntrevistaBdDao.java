package com.entrevistador.orquestador.infrastructure.adapter.dao;

import com.entrevistador.orquestador.dominio.excepciones.IdNoEncontradoException;
import com.entrevistador.orquestador.dominio.model.Entrevista;
import com.entrevistador.orquestador.dominio.port.EntrevistaDao;
import com.entrevistador.orquestador.infrastructure.adapter.entity.EntrevistaEntity;
import com.entrevistador.orquestador.infrastructure.adapter.repository.EntrevistaRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class EntrevistaBdDao implements EntrevistaDao {
    private final EntrevistaRepository entrevistaRepository;

    public EntrevistaBdDao(EntrevistaRepository entrevistaRepository) {
        this.entrevistaRepository = entrevistaRepository;
    }

    @Override
    public Mono<String> crearEntrevista() {
        return this.entrevistaRepository.save(EntrevistaEntity.builder().build())
                .map(EntrevistaEntity::getUuid);
    }

    @Override
    public Mono<String> actualizarEntrevista(Entrevista entrevista) {
        return entrevistaRepository.findById(entrevista.getUuid())
                .switchIfEmpty(Mono.error(new IdNoEncontradoException("Id de estado no encontrado. ID: " + entrevista.getUuid())))
                .flatMap(entrevistaEntity -> {

                    if (entrevista.getHojaDeVidaDto() == null) {
                        return entrevistaRepository.save(EntrevistaEntity.builder()
                                        .uuid(entrevistaEntity.getUuid())
                                        .nombre(entrevistaEntity.getNombre())
                                        .perfil(entrevistaEntity.getPerfil())
                                        .seniority(entrevistaEntity.getSeniority())
                                        .tecnologiasPrincipales(entrevistaEntity.getTecnologiasPrincipales())
                                        .experienciasLaborales(entrevistaEntity.getExperienciasLaborales())
                                        .habilidadesTecnicas(entrevistaEntity.getHabilidadesTecnicas())
                                        .certificaciones(entrevistaEntity.getCertificaciones())
                                        .proyectos(entrevistaEntity.getProyectos())
                                        .nivelIngles(entrevistaEntity.getNivelIngles())
                                        .otrasHabilidades(entrevistaEntity.getOtrasHabilidades())
                                        .empresa(entrevista.getInformacionEmpresaDto().getEmpresa())
                                        .perfilEmpresa(entrevista.getInformacionEmpresaDto().getPerfil())
                                        .seniorityEmpresa(entrevista.getInformacionEmpresaDto().getSeniority())
                                        .pais(entrevista.getInformacionEmpresaDto().getPais())
                                        .preguntas(entrevista.listToString(entrevista.getInformacionEmpresaDto().getPreguntas()))
                                        .build())
                                .map(EntrevistaEntity::getSeniorityEmpresa);
                    }

                    if (entrevista.getInformacionEmpresaDto() == null) {
                        return entrevistaRepository.save(EntrevistaEntity.builder()
                                        .uuid(entrevistaEntity.getUuid())
                                        .nombre(entrevista.getHojaDeVidaDto().getNombre())
                                        .perfil(entrevista.getHojaDeVidaDto().getPerfil())
                                        .seniority(entrevista.getHojaDeVidaDto().getSeniority())
                                        .tecnologiasPrincipales(entrevista.listToString(entrevista.getHojaDeVidaDto().getTecnologiasPrincipales()))
                                        .experienciasLaborales(entrevista.listToString(entrevista.getHojaDeVidaDto().getExperienciasLaborales()))
                                        .habilidadesTecnicas(entrevista.listToString(entrevista.getHojaDeVidaDto().getHabilidadesTecnicas()))
                                        .certificaciones(entrevista.listToString(entrevista.getHojaDeVidaDto().getCertificaciones()))
                                        .proyectos(entrevista.listToString(entrevista.getHojaDeVidaDto().getProyectos()))
                                        .nivelIngles(entrevista.getHojaDeVidaDto().getNivelIngles())
                                        .otrasHabilidades(entrevista.listToString(entrevista.getHojaDeVidaDto().getOtrasHabilidades()))
                                        .empresa(entrevistaEntity.getEmpresa())
                                        .perfilEmpresa(entrevistaEntity.getPerfilEmpresa())
                                        .seniorityEmpresa(entrevistaEntity.getSeniorityEmpresa())
                                        .pais(entrevistaEntity.getPais())
                                        .preguntas(entrevistaEntity.getPreguntas())
                                        .build())
                                .map(EntrevistaEntity::getSeniority);
                    } else {
                        return Mono.just("");
                    }

                });
    }

}
