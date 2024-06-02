package com.entrevistador.orquestador.infrastructure.adapter.mapper;

import com.entrevistador.orquestador.dominio.model.ProcesoEntrevista;
import com.entrevistador.orquestador.infrastructure.adapter.entity.ProcesoEntrevistaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProcesoEntrevistaMapper {
    @Mapping(target = "fechaYHora", source = "fechaHora")
    ProcesoEntrevista mapProcesoEntrevistaEntityToProcesoEntrevista(ProcesoEntrevistaEntity procesoEntrevistaEntity);

    @Mapping(target = "fechaHora", source = "fechaYHora")
    ProcesoEntrevistaEntity mapProcesoEntrevistaToProcesoEntrevistaEntity(ProcesoEntrevista procesoEntrevista);
}
