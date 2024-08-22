package com.entrevistador.orquestador.infrastructure.adapter.dto;

import com.entrevistador.orquestador.infrastructure.adapter.constants.ValidationsMessagesData;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonSerialize
public class PerfilDto {
    @NotNull(message = ValidationsMessagesData.NOT_NULL_MESSAGE)
    private String uuid;
    @NotNull(message = ValidationsMessagesData.NOT_NULL_MESSAGE)
    @Pattern(regexp = "[a-zA-ZáéíóúüñÁÉÍÓÚÜÑ\\\\s]*", message = ValidationsMessagesData.PATTERN_A_Z_MESSAGE)
    private String nombre;
    @NotNull(message = ValidationsMessagesData.NOT_NULL_MESSAGE)
    private String perfil;
    private String seniority;
    private List<String> tecnologiasPrincipales;
    private List<String> experienciasLaborales;
    private List<String> habilidadesTecnicas;
    private List<String> certificaciones;
    private List<String> proyectos;
    private String nivelIngles;
    private List<String> otrasHabilidades;
    private LocalDateTime fechaCreacion;
}
