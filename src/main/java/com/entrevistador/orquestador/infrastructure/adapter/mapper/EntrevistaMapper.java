package com.entrevistador.orquestador.infrastructure.adapter.mapper;

import com.entrevistador.orquestador.dominio.model.*;
import com.entrevistador.orquestador.infrastructure.adapter.dto.*;
import com.entrevistador.orquestador.dominio.model.enums.EstadoEntrevistaEnum;
import com.entrevistador.orquestador.infrastructure.adapter.entity.EntrevistaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EntrevistaMapper {
    Formulario mapFormularioDtoToFormulario(FormularioDto formularioDto);

    @Mapping(target = "idHojaDeVidaRag", source = "idHojaDeVidaRag")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "seniorityEmpresa", source = "formulario.seniority")
    @Mapping(target = "perfilEmpresa", source = "formulario.perfil")
    @Mapping(target = "estadoEntrevista", expression = "java(defaultEstadoEntrevista())")
    @Mapping(target = "fechaCreacion", expression = "java(defaultFechaCreacion())")
    EntrevistaEntity mapFormularioToEntrevistaEntity(String idHojaDeVidaRag, String username, Formulario formulario);

    default String defaultEstadoEntrevista() {
        return EstadoEntrevistaEnum.IC.name();
    }

    default LocalDateTime defaultFechaCreacion() {
        return LocalDateTime.now(ZoneOffset.UTC);
    }

    @Mapping(target = "idEntrevista", source = "uuid")
    EstadoEntrevista mapEntrevistaEntityToEstadoEntrevista(EntrevistaEntity entrevistaEntity);

    EstadoEntrevistaDto mapEstadoEntrevistaToEstadoEntrevistaDto(EstadoEntrevista estadoEntrevista);

    @Mapping(target = "uuid", source = "entrevistaEntity.uuid")
    @Mapping(target = "idHojaDeVidaRag", source = "entrevistaEntity.idHojaDeVidaRag")
    @Mapping(target = "idInformacionEmpresaRag", source = "entrevista.informacionEmpresa.idInformacionEmpresaRag")
    @Mapping(target = "empresa", source = "entrevista.informacionEmpresa.empresa")
    @Mapping(target = "perfilEmpresa", source = "entrevista.informacionEmpresa.perfil")
    @Mapping(target = "seniorityEmpresa", source = "entrevista.informacionEmpresa.seniority")
    @Mapping(target = "pais", source = "entrevista.informacionEmpresa.pais")
    @Mapping(target = "hojaDeVidaValida", source = "entrevistaEntity.hojaDeVidaValida")
    @Mapping(target = "estadoEntrevista", source = "entrevistaEntity.estadoEntrevista")
    @Mapping(target = "username", source = "entrevistaEntity.username")
    @Mapping(target = "fechaCreacion", source = "entrevistaEntity.fechaCreacion")
    EntrevistaEntity mapEntrevistaToEntrevistaEntity(EntrevistaEntity entrevistaEntity, Entrevista entrevista);

    SolicitudMatchDto mapSolicitudMatchToSolicitudMatchDto(SolicitudMatch solicitudMatch);

    PosicionEntrevistaDto mapPosicionEntrevistaToPosicionEntrevistaDto(PosicionEntrevista posicionEntrevista);

    SolicitudHojaDeVidaDto mapSolicitudHojaDeVidaSolicitudHojaDeVidaDto(SolicitudHojaDeVida solicitudHojaDeVida);

    SolicitudGeneracionEntrevistaDto mapSolicitudGeneracionEntrevistaToSolicitudGeneracionEntrevistaDto(SolicitudGeneracionEntrevista solicitudGeneracionEntrevista);

    EntrevistaUsuarioDto mapEntrevistaUsuarioToEntrevistaUsuarioDto(EntrevistaUsuario entrevistaUsuario);

    EntrevistaUsuario mapEntrevistaEntityToEntrevistaUsuario(EntrevistaEntity entrevistaEntity);
}
