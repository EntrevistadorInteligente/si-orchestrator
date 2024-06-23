package com.entrevistador.orquestador.infrastructure.adapter.mapper;

import com.entrevistador.orquestador.dominio.model.Notificacion;
import com.entrevistador.orquestador.infrastructure.adapter.dto.NotificacionDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface NotificacionesMapper {

    NotificacionDto mapNotificacionToNotificacionDto(Notificacion notificacion);
}
