package com.entrevistador.orquestador.infrastructure.adapter.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Builder
@Document(collection = "hoja_de_vida")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HojaDeVidaEntity {
    @Id
    private String uuid;
    private String username;
    private String idHojaDeVidaRag;
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
}
