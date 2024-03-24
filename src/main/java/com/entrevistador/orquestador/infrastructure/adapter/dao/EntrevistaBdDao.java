package com.entrevistador.orquestador.infrastructure.adapter.dao;

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
        /*return Mono.just(this.entrevistaRepository.save(new EntrevistaEntity()))
                .map(EntrevistaEntity::getUuid);*/

        return Mono.just("9d2bf204-8f5f-48a1-beff-b83b9db49855");
    }

    @Override
    public Mono<String> actualizarEntrevista(Entrevista entrevista){
        Optional<EntrevistaEntity> entrevistaEntity = entrevistaRepository.findById(entrevista.getUuid());
        if (entrevistaEntity.isEmpty()){
            return null;
        }
        if(entrevista.getHojaDeVidaDto().equals(null)){
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
                    .alertas(entrevistaEntity.get().getAlertas())
                    .nombreEmpresa(entrevista.getInformacionEmpresaDto().getNombreEmpresa())
                    .puesto(entrevista.getInformacionEmpresaDto().getPuesto())
                    .build());
            return Mono.just(entrevistaEntity.get().getSeniority());
        }
        if(entrevista.getInformacionEmpresaDto().equals(null)){
            entrevistaRepository.save(EntrevistaEntity.builder()
                    .uuid(entrevistaEntity.get().getUuid())
                    .nombre(entrevista.getHojaDeVidaDto().getNombre())
                    .perfil(entrevista.getHojaDeVidaDto().getPerfil())
                    .seniority(entrevista.getHojaDeVidaDto().getSeniority())
                    .tecnologiasPrincipales(entrevista.getHojaDeVidaDto().getTecnologiasPrincipales())
                    .experienciasLaborales(entrevista.getHojaDeVidaDto().getExperienciasLaborales())
                    .habilidadesTecnicas(entrevista.getHojaDeVidaDto().getHabilidadesTecnicas())
                    .certificaciones(entrevista.getHojaDeVidaDto().getCertificaciones())
                    .proyectos(entrevista.getHojaDeVidaDto().getProyectos())
                    .nivelIngles(entrevista.getHojaDeVidaDto().getNivelIngles())
                    .otrasHabilidades(entrevista.getHojaDeVidaDto().getOtrasHabilidades())
                    .alertas(entrevista.getHojaDeVidaDto().getAlertas())
                    .nombreEmpresa(entrevistaEntity.get().getNombreEmpresa())
                    .puesto(entrevistaEntity.get().getPuesto())
                    .build());
            return Mono.just(entrevistaEntity.get().getPuesto());
        }

        return Mono.empty();
    }

}
