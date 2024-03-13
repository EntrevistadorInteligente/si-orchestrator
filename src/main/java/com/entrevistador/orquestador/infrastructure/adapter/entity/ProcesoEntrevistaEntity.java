package com.entrevistador.orquestador.infrastructure.adapter.entity;

import com.entrevistador.orquestador.dominio.model.dto.ProcesoEntrevistaDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "PROCESO_ENTREVISTA")
@AllArgsConstructor
@Builder
public class ProcesoEntrevistaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "proceso_entrevista_id")
    @NonNull
    private String procesoEntrevistaId;
    @Column(name = "fecha_hora")
    @NonNull
    private String fechaHora;
    @NonNull
    private String estado;
    @NonNull
    private String fuente;
    @NonNull
    private String error;

    public void actualizar(ProcesoEntrevistaDto procesoEntrevistaDto){
        this.fechaHora= LocalDate.now().toString();
        this.estado = procesoEntrevistaDto.getEstado().toString();
        this.fuente = procesoEntrevistaDto.getFuente().toString();
        this.error = procesoEntrevistaDto.getError();
    }

}
