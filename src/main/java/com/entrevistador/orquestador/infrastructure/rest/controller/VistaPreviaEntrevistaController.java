package com.entrevistador.orquestador.infrastructure.rest.controller;

import com.entrevistador.orquestador.application.usescases.SolicitudEntrevista;
import com.entrevistador.orquestador.dominio.model.dto.VistaPreviaEntrevistaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/v1/entrevistador/public")
@RequiredArgsConstructor
public class VistaPreviaEntrevistaController {

    private final SolicitudEntrevista solicitudEntrevista;

    @GetMapping(value = "/preguntas")
    public List<VistaPreviaEntrevistaDto> crearSolicitudEntrevista() {

        List<VistaPreviaEntrevistaDto> vistaPreviaEntrevistaDtos = new ArrayList<>();
        vistaPreviaEntrevistaDtos.add(new VistaPreviaEntrevistaDto("Cual es tu mayor reto?"));
        vistaPreviaEntrevistaDtos.add(new VistaPreviaEntrevistaDto("Que lenguaje domians mas?"));
        vistaPreviaEntrevistaDtos.add(new VistaPreviaEntrevistaDto("Por que te gusta programar?"));
        vistaPreviaEntrevistaDtos.add(new VistaPreviaEntrevistaDto("Eres un pato?"));

        return new ArrayList<>(vistaPreviaEntrevistaDtos);

    }

}

