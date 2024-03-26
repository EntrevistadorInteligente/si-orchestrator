package com.entrevistador.orquestador.infrastructure.adapter.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Builder
@Document(collection = "entrevista")
public class EntrevistaEntity {
    /**
     * TODO
     * Cambiar por uuid
     */
    @Id
    private String uuid;


}
