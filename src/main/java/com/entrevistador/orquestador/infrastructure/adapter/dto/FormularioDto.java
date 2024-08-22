package com.entrevistador.orquestador.infrastructure.adapter.dto;


import com.entrevistador.orquestador.infrastructure.adapter.constants.ValidationsMessagesData;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FormularioDto {
    @NotNull(message = ValidationsMessagesData.NOT_NULL_MESSAGE)
    private String empresa;
    @NotNull(message = ValidationsMessagesData.NOT_NULL_MESSAGE)
    private String perfil;
    @NotNull(message = ValidationsMessagesData.NOT_NULL_MESSAGE)
    private String seniority;
    @NotNull(message = ValidationsMessagesData.NOT_NULL_MESSAGE)
    private String pais;
    @NotNull(message = ValidationsMessagesData.NOT_NULL_MESSAGE)
    private String descripcionVacante;
}
