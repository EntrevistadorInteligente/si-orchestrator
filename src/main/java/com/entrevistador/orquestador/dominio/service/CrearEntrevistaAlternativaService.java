package com.entrevistador.orquestador.dominio.service;

import com.entrevistador.orquestador.dominio.model.enums.EstadoProcesoEnum;
import com.entrevistador.orquestador.infrastructure.adapter.entity.ProcesoEntrevistaEntity;
import com.entrevistador.orquestador.infrastructure.adapter.repository.ProcesoEntrevistaRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CrearEntrevistaAlternativaService {

    private final NotificarFrontEntrevistaFallidaService notificarFrontEntrevistaFallidaService;
    private final ProcesoEntrevistaRepository procesoEntrevistaRepository;

    public String ejecutar(String idEvento, EstadoProcesoEnum estadoProcesoEnum, String idEntrevista){
        this.procesoEntrevistaRepository.save(new ProcesoEntrevistaEntity());
        return null;
    }

}
