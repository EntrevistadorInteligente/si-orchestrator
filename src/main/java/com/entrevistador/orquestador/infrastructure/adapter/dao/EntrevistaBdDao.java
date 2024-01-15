package com.entrevistador.orquestador.infrastructure.adapter.dao;

import com.entrevistador.orquestador.dominio.port.EntrevistaDao;
import com.entrevistador.orquestador.infrastructure.adapter.repository.EntrevistaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class EntrevistaBdDao implements EntrevistaDao {

    private final EntrevistaRepository entrevistaRepository;

    public EntrevistaBdDao(EntrevistaRepository entrevistaRepository) {
        this.entrevistaRepository = entrevistaRepository;
    }
}
