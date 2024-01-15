package com.entrevistador.orquestador.infrastructure.adapter.repository;

import com.entrevistador.orquestador.infrastructure.adapter.entity.EntrevistaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntrevistaRepository extends JpaRepository<EntrevistaEntity, String> {
}
