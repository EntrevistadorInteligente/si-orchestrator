package com.entrevistador.orquestador.infrastructure.adapter.mapper;

import com.entrevistador.orquestador.infrastructure.adapter.dto.EntrevistaDto;
import com.entrevistador.orquestador.infrastructure.adapter.dto.FeedbackDto;
import com.entrevistador.orquestador.infrastructure.adapter.dto.HojaDeVidaDto;
import com.entrevistador.orquestador.infrastructure.adapter.dto.MensajeAnalizadorEmpresaDto;
import com.entrevistador.orquestador.infrastructure.adapter.dto.MensajeValidacionMatchDto;
import com.entrevistador.orquestador.infrastructure.adapter.dto.PerfilDto;
import com.entrevistador.orquestador.dominio.model.EntrevistaModel;
import com.entrevistador.orquestador.dominio.model.Feedback;
import com.entrevistador.orquestador.dominio.model.HojaDeVidaModel;
import com.entrevistador.orquestador.dominio.model.MensajeAnalizadorEmpresa;
import com.entrevistador.orquestador.dominio.model.MensajeValidacionMatch;
import com.entrevistador.orquestador.dominio.model.Perfil;
import com.entrevistador.orquestador.infrastructure.adapter.entity.HojaDeVidaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface HojaDeVidaMapper {
    @Mapping(target = "fechaCreacion", source = "hojaDeVidaEntity.fechaCreacion")
    HojaDeVidaModel mapHojaDeVidaEntityToHojaDeVida(HojaDeVidaEntity hojaDeVidaEntity);

    @Mapping(target = "fechaCreacion", source = "hojaDeVidaModel.fechaCreacion")
    PerfilDto mapHojaDeVidaToPerfilDto(HojaDeVidaModel hojaDeVidaModel);

    HojaDeVidaModel mapHojaDeVidaDtoToHojaDeVida(HojaDeVidaDto hojaDeVidaDto);

    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "fechaCreacion", expression = "java(defaultFechaCreacion())")
    HojaDeVidaEntity mapHojaDeVidaModelToHojaDeVidaEntity(HojaDeVidaModel hojaDeVidaModel);

    default LocalDateTime defaultFechaCreacion() {
        return LocalDateTime.now(ZoneOffset.UTC);
    }

    @Mapping(target = "fechaCreacion",  source = "perfilDto.fechaCreacion")
    Perfil mapPerfilDtoToPerfil(PerfilDto perfilDto);

    MensajeValidacionMatch mapMensajeValidacionMatchDtoToMensajeValidacionMatch(MensajeValidacionMatchDto mensajeValidacionMatchDto);

    MensajeAnalizadorEmpresa mapMensajeAnalizadorEmpresaDtoToMensajeAnalizadorEmpresa(MensajeAnalizadorEmpresaDto mensajeAnalizadorEmpresaDto);

    Feedback mapFeedbackDtoToFeedback(FeedbackDto feedbackDto);

    EntrevistaModel mapEntrevistaDtoToEntrevistaModel(EntrevistaDto json);
}
