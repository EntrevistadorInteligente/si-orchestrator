package com.entrevistador.orquestador.infrastructure.adapter.entity;

import com.entrevistador.orquestador.dominio.model.enums.EstadoProcesoEnum;
import com.entrevistador.orquestador.dominio.model.enums.FuenteEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@Document(collection = "proceso_entrevista")
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ProcesoEntrevistaEntity {

    @Id
    private String uuid;
    private Date fechaHora;
    private EstadoProcesoEnum estado;
    private FuenteEnum fuente;
    private String error;

}
