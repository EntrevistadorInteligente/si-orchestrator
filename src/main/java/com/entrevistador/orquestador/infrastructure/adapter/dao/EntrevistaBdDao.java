package com.entrevistador.orquestador.infrastructure.adapter.dao;

import com.entrevistador.orquestador.dominio.excepciones.IdNoEncontradoException;
import com.entrevistador.orquestador.dominio.model.Entrevista;
import com.entrevistador.orquestador.dominio.model.dto.HojaDeVidaDto;
import com.entrevistador.orquestador.dominio.model.dto.InformacionEmpresaDto;
import com.entrevistador.orquestador.dominio.port.EntrevistaDao;
import com.entrevistador.orquestador.infrastructure.adapter.entity.EntrevistaEntity;
import com.entrevistador.orquestador.infrastructure.adapter.repository.EntrevistaRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Repository
public class EntrevistaBdDao implements EntrevistaDao {

    private final EntrevistaRepository entrevistaRepository;

    public EntrevistaBdDao(EntrevistaRepository entrevistaRepository) {
        this.entrevistaRepository = entrevistaRepository;
    }

    @Override
    public Mono<String> crearEntrevista() {
        //TODO: Cambiar a una DBMS Reactivo
        return Mono.just(this.entrevistaRepository.save(EntrevistaEntity.builder().build()))
                .map(EntrevistaEntity::getUuid);

    }

    @Override
    public Mono<String> actualizarEntrevista(Entrevista entrevista){
        String mensajeError = "No se encontro la entrevista con el id: %s";
        Optional<EntrevistaEntity> entrevistaEntity = entrevistaRepository.findById(entrevista.getUuid());
        if (entrevistaEntity.isEmpty()){
            throw new IdNoEncontradoException(String.format(mensajeError, entrevista.getUuid()));
        }
        if(entrevista.getHojaDeVidaDto() == null){
            entrevistaRepository.save(EntrevistaEntity.builder()
                    .uuid(entrevistaEntity.get().getUuid())
                    .nombre(entrevistaEntity.get().getNombre())
                    .perfil(entrevistaEntity.get().getPerfil())
                    .seniority(entrevistaEntity.get().getSeniority())
                    .tecnologiasPrincipales(entrevistaEntity.get().getTecnologiasPrincipales())
                    .experienciasLaborales(entrevistaEntity.get().getExperienciasLaborales())
                    .habilidadesTecnicas(entrevistaEntity.get().getHabilidadesTecnicas())
                    .certificaciones(entrevistaEntity.get().getCertificaciones())
                    .proyectos(entrevistaEntity.get().getProyectos())
                    .nivelIngles(entrevistaEntity.get().getNivelIngles())
                    .otrasHabilidades(entrevistaEntity.get().getOtrasHabilidades())
                    .empresa(entrevista.getInformacionEmpresaDto().getEmpresa())
                    .perfilEmpresa(entrevista.getInformacionEmpresaDto().getPerfil())
                    .seniorityEmpresa(entrevista.getInformacionEmpresaDto().getSeniority())
                    .pais(entrevista.getInformacionEmpresaDto().getPais())
                    .preguntas(entrevista.listToString(entrevista.getInformacionEmpresaDto().getPreguntas()))
                    .build());
            return Mono.just(entrevistaEntity.get().getSeniority());
        }
        if(entrevista.getInformacionEmpresaDto() == null){
            entrevistaRepository.save(EntrevistaEntity.builder()
                    .uuid(entrevistaEntity.get().getUuid())
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
                    .empresa(entrevistaEntity.get().getEmpresa())
                    .perfilEmpresa(entrevistaEntity.get().getPerfilEmpresa())
                    .seniorityEmpresa(entrevistaEntity.get().getSeniorityEmpresa())
                    .pais(entrevistaEntity.get().getPais())
                    .preguntas(entrevistaEntity.get().getPreguntas())
                    .build());
            return Mono.just(entrevistaEntity.get().getPerfilEmpresa());
        }

        return Mono.just("");
    }

}
