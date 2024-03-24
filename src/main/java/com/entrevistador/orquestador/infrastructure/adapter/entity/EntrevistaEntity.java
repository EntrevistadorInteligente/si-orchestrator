package com.entrevistador.orquestador.infrastructure.adapter.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

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
    private List<String> tecnologiasPrincipales;
    private List<String> experienciasLaborales;
    private List<String> habilidadesTecnicas;
    private List<String> certificaciones;
    private List<String> proyectos;
    private String nivelIngles;
    private List<String> otrasHabilidades;
    private String alertas;

    private String nombreEmpresa;
    private String puesto;


}
