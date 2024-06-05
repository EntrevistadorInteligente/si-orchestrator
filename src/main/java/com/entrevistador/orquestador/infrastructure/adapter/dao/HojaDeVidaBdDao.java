package com.entrevistador.orquestador.infrastructure.adapter.dao;

import com.entrevistador.orquestador.dominio.model.Perfil;
import com.entrevistador.orquestador.infrastructure.adapter.mapper.HojaDeVidaMapper;
import com.entrevistador.orquestador.dominio.excepciones.IdHojaDeVidaRagNuloException;
import com.entrevistador.orquestador.dominio.excepciones.UsernameNoEncontradoException;
import com.entrevistador.orquestador.dominio.model.HojaDeVidaModel;
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
    private final HojaDeVidaMapper mapper;

    public Mono<Void> guardarHojaDeVida(HojaDeVidaModel hojaDeVidaModel){
        return Mono.just(this.mapper.mapHojaDeVidaModelToHojaDeVidaEntity(hojaDeVidaModel))
                .flatMap(this.hojaDeVidaRepository::save)
                .then();
    }

    public Mono<String> obtenerIdHojaDeVidaRag(String username){
        final String usernameError = "Username no encontrado. Username: %s";
        final String idRagError = "El campo idHojaDeVidaRag del username \"%s\" es nulo";
        return this.hojaDeVidaRepository.findFirstByUsernameOrderByFechaCreacionDesc(username)
                .switchIfEmpty(Mono.error(new UsernameNoEncontradoException(String.format(usernameError, username))))
                .map(HojaDeVidaEntity::getIdHojaDeVidaRag)
                .switchIfEmpty(Mono.error(new IdHojaDeVidaRagNuloException(String.format(idRagError, username))));
    }

    public Mono<HojaDeVidaModel> obtenerHojaDeVidaPorNombreUsuario(String username){
        return this.hojaDeVidaRepository.findFirstByUsernameOrderByFechaCreacionDesc(username)
                .map(this.mapper::mapHojaDeVidaEntityToHojaDeVida);
    }

    @Override
    public Mono<Void> actualizarDatosPerfil(String uuid, Perfil perfil) {
        return this.hojaDeVidaRepository.findById(uuid)
                .flatMap(hojaDeVidaEntity -> this.hojaDeVidaRepository.save(HojaDeVidaEntity.builder()
                        .uuid(hojaDeVidaEntity.getUuid())
                        .username(hojaDeVidaEntity.getUsername())
                        .idHojaDeVidaRag(hojaDeVidaEntity.getIdHojaDeVidaRag())
                        .nombre(perfil.getNombre())
                        .perfil(perfil.getPerfil())
                        .seniority(perfil.getSeniority())
                        .tecnologiasPrincipales(perfil.getTecnologiasPrincipales())
                        .experienciasLaborales(perfil.getExperienciasLaborales())
                        .habilidadesTecnicas(perfil.getHabilidadesTecnicas())
                        .certificaciones(perfil.getCertificaciones())
                        .proyectos(perfil.getProyectos())
                        .nivelIngles(perfil.getNivelIngles())
                        .otrasHabilidades(perfil.getOtrasHabilidades())
                        .build())).then();
    }
}
