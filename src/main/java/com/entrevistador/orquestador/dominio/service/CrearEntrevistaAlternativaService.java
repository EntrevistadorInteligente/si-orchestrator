package com.entrevistador.orquestador.dominio.service;

import com.entrevistador.orquestador.infrastructure.adapter.entity.ProcesoEntrevistaEntity;
import com.entrevistador.orquestador.infrastructure.adapter.repository.ProcesoEntrevistaRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CrearEntrevistaAlternativaService {

    private final ProcesoEntrevistaRepository procesoEntrevistaRepository;

    public String ejecutar() {
        this.procesoEntrevistaRepository.save(ProcesoEntrevistaEntity.builder().build());
        return null;
    }

}
