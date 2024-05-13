package com.entrevistador.orquestador.infrastructure.rest.controller;

import com.entrevistador.orquestador.application.usescases.SolicitudEntrevista;
import com.entrevistador.orquestador.dominio.model.dto.FormularioDto;
import com.entrevistador.orquestador.dominio.model.dto.VistaPreviaEntrevistaDto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.entrevistador.orquestador.infrastructure.adapter.constants.ValidationsMessagesData;
import com.entrevistador.orquestador.infrastructure.adapter.util.SanitizeStringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/v1/entrevistadores")
@RequiredArgsConstructor
@Validated
public class EntrevistaController {

    private final SolicitudEntrevista solicitudEntrevista;

    @PostMapping(value = "/solicitudes-entrevistas")
    public Mono<ResponseEntity<String>> crearSolicitudEntrevista(
            @NotNull(message = ValidationsMessagesData.NOT_NULL_MESSAGE) @RequestParam String username,
            @Valid @RequestBody FormularioDto formulario
    ) {
        return this.solicitudEntrevista.generarSolicitudEntrevista(SanitizeStringUtil.sanitize(username), formulario)
                .then(Mono.just(ResponseEntity.status(HttpStatus.CREATED)
                        .body("Archivo PDF cargado con exito")));
    }

    @GetMapping(value = "/preguntas")
    public List<VistaPreviaEntrevistaDto> crearSolicitudEntrevista(
            @NotNull(message = ValidationsMessagesData.NOT_NULL_MESSAGE) @RequestParam("posicion") String posicion) {
        return new ArrayList<>(solicitudEntrevista.generarPreguntas(SanitizeStringUtil.sanitize(posicion)));
    }

}