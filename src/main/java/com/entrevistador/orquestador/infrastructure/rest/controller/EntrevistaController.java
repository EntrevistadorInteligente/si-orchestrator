package com.entrevistador.orquestador.infrastructure.rest.controller;

import com.entrevistador.orquestador.application.usescases.SolicitudEntrevista;
import com.entrevistador.orquestador.dominio.model.dto.FormularioDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/api/v1/entrevistador/orquestador")
@RequiredArgsConstructor
public class EntrevistaController {

    private final SolicitudEntrevista solicitudEntrevista;

    @PostMapping(value = "/cv")
    public Mono<ResponseEntity<String>> crearSolicitudEntrevista(
            @RequestPart("file") Mono<FilePart> file,
            @RequestPart("formulario") FormularioDto formularioDto
    ) {
        return this.solicitudEntrevista.generarSolicitudEntrevista(file, formularioDto)
                .then(Mono.just(ResponseEntity.status(HttpStatus.CREATED)
                        .body("Archivo PDF cargado con exito")));
    }

}

