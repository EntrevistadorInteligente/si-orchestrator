package com.entrevistador.orquestador.dominio.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HojaDeVidaDto {
    private String uuid;
    private String username;
    @JsonProperty("id_hoja_de_vida_rag")
    private String idHojaDeVidaRag;
    private String nombre;
    private String perfil;
    private String seniority;
    @JsonProperty("tecnologias_principales")
    private List<String> tecnologiasPrincipales;
    @JsonProperty("experiencias_laborales")
    private List<String> experienciasLaborales;
    @JsonProperty("habilidades_tecnicas")
    private List<String> habilidadesTecnicas;
    private List<String> certificaciones;
    private List<String> proyectos;
    @JsonProperty("nivel_ingles")
    private String nivelIngles;
    @JsonProperty("otras_habilidades")
    private List<String> otrasHabilidades;
}
