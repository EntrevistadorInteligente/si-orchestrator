package com.entrevistador.orquestador.infrastructure.adapter.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "PROCESO_ENTREVISTA")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProcesoEntrevistaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "proceso_entrevista_id")
    private String procesoEntrevistaId;
    @Column(name = "fecha_hora")
    private String fechaHora;
    private String estado;
    private String fuente;
    private String error;

}
