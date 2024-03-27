package com.entrevistador.orquestador.infrastructure.adapter.entity;

import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Builder
@Document(collection = "entrevista")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EntrevistaEntity {
    @Id
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
