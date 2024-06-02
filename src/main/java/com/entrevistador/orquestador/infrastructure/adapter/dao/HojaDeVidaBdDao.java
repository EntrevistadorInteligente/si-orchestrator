package com.entrevistador.orquestador.infrastructure.adapter.dao;

import com.entrevistador.orquestador.dominio.excepciones.IdHojaDeVidaRagNuloException;
import com.entrevistador.orquestador.dominio.excepciones.UsernameNoEncontradoException;
import com.entrevistador.orquestador.dominio.model.dto.HojaDeVidaDto;
import com.entrevistador.orquestador.dominio.model.dto.PerfilDto;
import com.entrevistador.orquestador.dominio.port.HojaDeVidaDao;
import com.entrevistador.orquestador.infrastructure.adapter.entity.HojaDeVidaEntity;
import com.entrevistador.orquestador.infrastructure.adapter.repository.HojaDeVidaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class HojaDeVidaBdDao implements HojaDeVidaDao {
    private final HojaDeVidaRepository hojaDeVidaRepository;

    public Mono<Void> guardarHojaDeVida(HojaDeVidaDto hojaDeVidaDto){
        return this.hojaDeVidaRepository.save(HojaDeVidaEntity.builder()
                .username(hojaDeVidaDto.getUsername())
                .idHojaDeVidaRag(hojaDeVidaDto.getIdHojaDeVidaRag())
                .nombre(hojaDeVidaDto.getNombre())
                .perfil(hojaDeVidaDto.getPerfil())
                .seniority(hojaDeVidaDto.getSeniority())
                .tecnologiasPrincipales(hojaDeVidaDto.getTecnologiasPrincipales())
                .experienciasLaborales(hojaDeVidaDto.getExperienciasLaborales())
                .habilidadesTecnicas(hojaDeVidaDto.getHabilidadesTecnicas())
                .certificaciones(hojaDeVidaDto.getCertificaciones())
                .proyectos(hojaDeVidaDto.getProyectos())
                .nivelIngles(hojaDeVidaDto.getNivelIngles())
                .otrasHabilidades(hojaDeVidaDto.getOtrasHabilidades())
                .build()).then(Mono.empty());
    }

    public Mono<String> obtenerIdHojaDeVidaRag(String username){
        final String usernameError = "Username no encontrado. Username: %s";
        final String idRagError = "El campo idHojaDeVidaRag del username \"%s\" es nulo";
        return this.hojaDeVidaRepository.findByUsername(username)
                .switchIfEmpty(Mono.error(new UsernameNoEncontradoException(String.format(usernameError, username))))
                .map(HojaDeVidaEntity::getIdHojaDeVidaRag)
                .switchIfEmpty(Mono.error(new IdHojaDeVidaRagNuloException(String.format(idRagError, username))));
    }

    public Mono<HojaDeVidaDto> obtenerHojaDeVidaPorNombreUsuario(String username){
        return this.hojaDeVidaRepository.findByUsername(username)
                .map(HojaDeVidaBdDao::mapearHojaDeVida);
    }

    @Override
    public Mono<Void> actualizarDatosPerfil(String uuid, PerfilDto perfilDto) {
        return this.hojaDeVidaRepository.findById(uuid)
                .flatMap(hojaDeVidaEntity -> this.hojaDeVidaRepository.save(HojaDeVidaEntity.builder()
                        .uuid(hojaDeVidaEntity.getUuid())
                        .username(hojaDeVidaEntity.getUsername())
                        .idHojaDeVidaRag(hojaDeVidaEntity.getIdHojaDeVidaRag())
                        .nombre(perfilDto.getNombre())
                        .perfil(perfilDto.getPerfil())
                        .seniority(perfilDto.getSeniority())
                        .tecnologiasPrincipales(perfilDto.getTecnologiasPrincipales())
                        .experienciasLaborales(perfilDto.getExperienciasLaborales())
                        .habilidadesTecnicas(perfilDto.getHabilidadesTecnicas())
                        .certificaciones(perfilDto.getCertificaciones())
                        .proyectos(perfilDto.getProyectos())
                        .nivelIngles(perfilDto.getNivelIngles())
                        .otrasHabilidades(perfilDto.getOtrasHabilidades())
                        .build())).then();
    }

    private static HojaDeVidaDto mapearHojaDeVida(HojaDeVidaEntity entity) {
        return HojaDeVidaDto.builder()
                .uuid(entity.getUuid())
                .username(entity.getUsername())
                .nombre(entity.getNombre())
                .perfil(entity.getPerfil())
                .seniority(entity.getSeniority())
                .tecnologiasPrincipales(entity.getTecnologiasPrincipales())
                .experienciasLaborales(entity.getExperienciasLaborales())
                .habilidadesTecnicas(entity.getHabilidadesTecnicas())
                .certificaciones(entity.getCertificaciones())
                .proyectos(entity.getProyectos())
                .nivelIngles(entity.getNivelIngles())
                .otrasHabilidades(entity.getOtrasHabilidades())
                .build();
    }
}
