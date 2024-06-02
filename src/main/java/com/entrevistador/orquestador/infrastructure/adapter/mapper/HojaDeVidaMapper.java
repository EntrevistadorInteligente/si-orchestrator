package com.entrevistador.orquestador.infrastructure.adapter.mapper;

import com.entrevistador.orquestador.application.dto.EntrevistaDto;
import com.entrevistador.orquestador.application.dto.FeedbackDto;
import com.entrevistador.orquestador.application.dto.HojaDeVidaDto;
import com.entrevistador.orquestador.application.dto.MensajeAnalizadorEmpresaDto;
import com.entrevistador.orquestador.application.dto.MensajeValidacionMatchDto;
import com.entrevistador.orquestador.application.dto.PerfilDto;
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

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface HojaDeVidaMapper {
    HojaDeVidaModel mapHojaDeVidaEntityToHojaDeVida(HojaDeVidaEntity hojaDeVidaEntity);

    PerfilDto mapHojaDeVidaToPerfilDto(HojaDeVidaModel hojaDeVidaModel);

    HojaDeVidaModel mapHojaDeVidaDtoToHojaDeVida(HojaDeVidaDto hojaDeVidaDto);

    @Mapping(target = "uuid", ignore = true)
    HojaDeVidaEntity mapHojaDeVidaModelToHojaDeVidaEntity(HojaDeVidaModel hojaDeVidaModel);

    Perfil mapPerfilDtoToPerfil(PerfilDto perfilDto);

    MensajeValidacionMatch mapMensajeValidacionMatchDtoToMensajeValidacionMatch(MensajeValidacionMatchDto mensajeValidacionMatchDto);

    MensajeAnalizadorEmpresa mapMensajeAnalizadorEmpresaDtoToMensajeAnalizadorEmpresa(MensajeAnalizadorEmpresaDto mensajeAnalizadorEmpresaDto);

    Feedback mapFeedbackDtoToFeedback(FeedbackDto feedbackDto);

    EntrevistaModel mapEntrevistaDtoToEntrevistaModel(EntrevistaDto json);
}
