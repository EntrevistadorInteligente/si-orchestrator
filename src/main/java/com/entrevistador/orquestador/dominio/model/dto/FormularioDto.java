package com.entrevistador.orquestador.dominio.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FormularioDto {
    private String empresa;
    private String perfil;
    private String seniority;
    private String pais;
    private String descripcionVacante;
}
