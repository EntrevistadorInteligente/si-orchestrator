package com.entrevistador.orquestador.dominio.model.dto;

import lombok.Builder;

@Builder
public class PreparacionEntrevistaDto {
    private String idEntrevista;
    private  String eventoEntrevistaId;
    private  byte[] hojadevida;
}
