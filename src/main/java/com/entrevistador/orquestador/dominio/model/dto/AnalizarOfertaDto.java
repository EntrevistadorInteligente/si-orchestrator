package com.entrevistador.orquestador.dominio.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class AnalizarOfertaDto {

    private byte[] ofertaLaboral;

}
