package com.entrevistador.orquestador.dominio.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Perfil {
    private String       uuid;
    private String       nombre;
    private String       perfil;
    private String       seniority;
    private List<String> tecnologiasPrincipales;
    private List<String> experienciasLaborales;
    private List<String> habilidadesTecnicas;
    private List<String> certificaciones;
    private List<String> proyectos;
    private String       nivelIngles;
    private List<String> otrasHabilidades;
    private LocalDateTime fechaCreacion;
}
