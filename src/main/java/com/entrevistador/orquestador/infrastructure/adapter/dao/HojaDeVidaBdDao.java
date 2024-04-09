package com.entrevistador.orquestador.infrastructure.adapter.dao;

import com.entrevistador.orquestador.dominio.excepciones.IdHojaDeVidaRagNuloException;
import com.entrevistador.orquestador.dominio.excepciones.UsernameNoEncontradoException;
import com.entrevistador.orquestador.dominio.model.dto.HojaDeVidaDto;
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
                .idHojaDeVidaRag(hojaDeVidaDto.getIdHojaDeVidaRag())
                .certificaciones(hojaDeVidaDto.getCertificaciones())
                .habilidadesTecnicas(hojaDeVidaDto.getHabilidadesTecnicas())
                .nivelIngles(hojaDeVidaDto.getNivelIngles())
                .experienciasLaborales(hojaDeVidaDto.getExperienciasLaborales())
                .otrasHabilidades(hojaDeVidaDto.getOtrasHabilidades())
                .build()).then(Mono.empty());
    }

    public Mono<String> obtenerIdHojaDeVidaRag(String username){
        final String usernameError = "Username no encontrado. Username: %s";
        final String idRagError = "El campo idHojaDeVidaRag del username \"%s\" es nulo";
        return this.hojaDeVidaRepository.findByUsername(username)
                .switchIfEmpty(Mono.error(new UsernameNoEncontradoException(String.format(usernameError, username))))
                .flatMap(entity -> entity.getIdHojaDeVidaRag() == null ?
                        Mono.error(new IdHojaDeVidaRagNuloException(String.format(idRagError, username)))
                        :
                        Mono.just(entity.getIdHojaDeVidaRag()));
    }

    public Mono<HojaDeVidaDto> obtenerHojaDeVida(String username){
        return this.hojaDeVidaRepository.findByUsername(username)
                .map(entity -> HojaDeVidaDto.builder()
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
                        .build());
    }
}
