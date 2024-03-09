package com.entrevistador.orquestador.infrastructure.adapter.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "PROCESO_ENTREVISTA")
@AllArgsConstructor
@NoArgsConstructor
public class ProcesoEntrevistaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String procesoEntrevistaId;
    private String fechaHora;
    private String estado;
    private String fuente;
    private String error;

}
