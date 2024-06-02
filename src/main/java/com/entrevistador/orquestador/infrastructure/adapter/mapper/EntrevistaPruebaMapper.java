package com.entrevistador.orquestador.infrastructure.adapter.mapper;

import com.entrevistador.orquestador.application.dto.SoloPerfilDto;
import com.entrevistador.orquestador.dominio.model.IdEntrevista;
import com.entrevistador.orquestador.application.dto.IdEntrevistaDto;
import com.entrevistador.orquestador.dominio.model.SoloPerfil;
import com.entrevistador.orquestador.infrastructure.adapter.entity.EntrevistaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EntrevistaPruebaMapper {
    @Mapping(target = "id", source = "uuid")
    IdEntrevista mapEntrevistaEntityToIdEntrevista(EntrevistaEntity entrevistaEntity);

    IdEntrevistaDto mapIdEntrevistaDto(IdEntrevista idEntrevista);

    SoloPerfil mapEntrevistaEntityToSoloPerfil(EntrevistaEntity entrevistaEntity);

    SoloPerfilDto mapSoloPerfilToSoloPerfilDto(SoloPerfil soloPerfil);
}
