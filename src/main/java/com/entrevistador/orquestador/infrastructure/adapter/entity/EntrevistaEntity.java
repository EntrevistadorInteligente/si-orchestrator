package com.entrevistador.orquestador.infrastructure.adapter.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;


@Getter
@Entity
@Table(name = "ENTREVISTA")
@Builder
public class EntrevistaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;

    private String nombre;
    private String perfil;
    private String seniority;
    private String tecnologiasPrincipales;
    private String experienciasLaborales;
    private String habilidadesTecnicas;
    private String certificaciones;
    private String proyectos;
    private String nivelIngles;
    private String otrasHabilidades;

    private String empresa;
    private String perfilEmpresa;
    private String seniorityEmpresa;
    private String pais;
    private String preguntas;


}
