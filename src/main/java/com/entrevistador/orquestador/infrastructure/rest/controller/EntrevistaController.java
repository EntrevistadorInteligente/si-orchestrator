package com.entrevistador.orquestador.infrastructure.rest.controller;

import com.entrevistador.orquestador.application.usescases.SolicitudEntrevista;
import com.entrevistador.orquestador.dominio.model.dto.FormularioDto;
import com.entrevistador.orquestador.dominio.model.dto.VistaPreviaEntrevistaDto;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/v1/entrevistador")
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

    @GetMapping(value = "/preguntas")
    public List<VistaPreviaEntrevistaDto> crearSolicitudEntrevista(
            @RequestParam("posicion") String posicion) {
        return new ArrayList<>(solicitudEntrevista.generarPreguntas(posicion));
    }

}

