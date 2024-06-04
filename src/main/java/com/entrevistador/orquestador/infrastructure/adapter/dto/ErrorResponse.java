package com.entrevistador.orquestador.infrastructure.adapter.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ErrorResponse {
    private LocalDateTime fecha;
    private String codigo;
    private String mensaje;
}
