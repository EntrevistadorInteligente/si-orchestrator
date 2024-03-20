package com.entrevistador.orquestador.infrastructure.adapter.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class EntrevistaEntity {
    /**
     * TODO
     * Cambiar por uuid
     */
    @Id
    private String uuid;


}
