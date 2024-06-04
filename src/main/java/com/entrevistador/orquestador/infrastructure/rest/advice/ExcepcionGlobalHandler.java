package com.entrevistador.orquestador.infrastructure.rest.advice;

import com.entrevistador.orquestador.dominio.excepciones.PdfException;
import com.entrevistador.orquestador.infrastructure.adapter.dto.ErrorResponse;
import com.entrevistador.orquestador.dominio.model.enums.CodigoErrorEnum;
import com.entrevistador.orquestador.dominio.excepciones.EntrevistaProcesoException;
import com.entrevistador.orquestador.dominio.excepciones.LimiteEntrevistaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;


@ControllerAdvice
public class ExcepcionGlobalHandler {
    
    @ExceptionHandler(LimiteEntrevistaException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleEntrevistaLimitException(LimiteEntrevistaException ex) {
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(),ex.getCode(), ex.getMessage());
        return Mono.just(new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(EntrevistaProcesoException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleEntrevistaProcesoException(EntrevistaProcesoException ex) {
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(),ex.getCode(), ex.getMessage());
        return Mono.just(new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(PdfException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleHojaDeVidaInvalidaException(PdfException ex) {
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), ex.getCode(), ex.getMessage());
        return Mono.just(new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<ErrorResponse>> handleGenericException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), CodigoErrorEnum.INTERNAL_SERVER_ERROR.getCode(),
                "Internal Server Error");
        return Mono.just(new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
