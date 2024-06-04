package com.entrevistador.orquestador.dominio.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HojaDeVidaModel {
    private String       uuid;
    private String       username;
    private String       idHojaDeVidaRag;
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
}
