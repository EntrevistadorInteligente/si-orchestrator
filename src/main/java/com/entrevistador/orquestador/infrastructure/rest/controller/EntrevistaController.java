package com.entrevistador.orquestador.infrastructure.rest.controller;

import com.entrevistador.orquestador.application.usescases.SolicitudEntrevista;
import com.entrevistador.orquestador.dominio.model.dto.FormularioDto;
import com.entrevistador.orquestador.dominio.model.dto.VistaPreviaEntrevistaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;


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
    public List<VistaPreviaEntrevistaDto> crearSolicitudEntrevista() {

        List<VistaPreviaEntrevistaDto> vistaPreviaEntrevistaDtos = new ArrayList<>();
        vistaPreviaEntrevistaDtos.add(new VistaPreviaEntrevistaDto("Describe un proyecto complejo en el que hayas trabajado que incluyera tanto el desarrollo del frontend como del backend. ¿Cómo abordaste los desafíos técnicos y cómo aseguraste la cohesión entre ambas partes del proyecto?"));
        vistaPreviaEntrevistaDtos.add(new VistaPreviaEntrevistaDto("¿Cuáles son las diferencias que consideras más importantes entre React y Angular para el desarrollo del frontend y cómo influyen en tu elección para un proyecto específico?"));
        vistaPreviaEntrevistaDtos.add(new VistaPreviaEntrevistaDto("Describe una situación en la que hayas tenido un desacuerdo con un miembro del equipo sobre la implementación de una característica. ¿Cómo lo resolviste y qué aprendiste de esa experiencia?"));
        vistaPreviaEntrevistaDtos.add(new VistaPreviaEntrevistaDto("El mundo del desarrollo web está constantemente evolucionando con nuevas herramientas y prácticas. ¿Cómo te mantienes actualizado con las últimas tendencias y tecnologías, y podrías compartir un ejemplo de cómo aplicaste una nueva tecnología o práctica en un proyecto reciente?"));

        return new ArrayList<>(vistaPreviaEntrevistaDtos);

    }


}

