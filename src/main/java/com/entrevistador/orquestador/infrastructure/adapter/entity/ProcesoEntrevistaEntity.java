package com.entrevistador.orquestador.infrastructure.adapter.entity;

import com.entrevistador.orquestador.dominio.model.dto.ProcesoEntrevistaDto;
import com.entrevistador.orquestador.dominio.model.enums.EstadoProcesoEnum;
import com.entrevistador.orquestador.dominio.model.enums.FuenteEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "PROCESO_ENTREVISTA")
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ProcesoEntrevistaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "proceso_entrevista_id")
    private String procesoEntrevistaId;
    @Column(name = "fecha_hora")
    private Date fechaHora;
    private EstadoProcesoEnum estado;
    private FuenteEnum fuente;
    private String error;

}
