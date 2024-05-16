package com.entrevistador.orquestador.infrastructure.rest.controller;

import com.entrevistador.orquestador.application.usescases.EntrevistaPrueba;
import com.entrevistador.orquestador.application.usescases.SolicitudEntrevista;
import com.entrevistador.orquestador.dominio.model.dto.IdEntrevistaDto;
import com.entrevistador.orquestador.dominio.model.dto.SoloPerfilImp;
import com.entrevistador.orquestador.dominio.model.dto.VistaPreviaEntrevistaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/v1/entrevistador/public")
@RequiredArgsConstructor
public class VistaPreviaEntrevistaController {

    private final SolicitudEntrevista solicitudEntrevista;
    private final EntrevistaPrueba entrevistaPrueba;

    @GetMapping(value = "/preguntas")
    public List<VistaPreviaEntrevistaDto> crearSolicitudEntrevista() {

        List<VistaPreviaEntrevistaDto> vistaPreviaEntrevistaDtos = new ArrayList<>();
        vistaPreviaEntrevistaDtos.add(new VistaPreviaEntrevistaDto("Cual es tu mayor reto?"));
        vistaPreviaEntrevistaDtos.add(new VistaPreviaEntrevistaDto("Que lenguaje domians mas?"));
        vistaPreviaEntrevistaDtos.add(new VistaPreviaEntrevistaDto("Por que te gusta programar?"));
        vistaPreviaEntrevistaDtos.add(new VistaPreviaEntrevistaDto("Eres un pato?"));

        return new ArrayList<>(vistaPreviaEntrevistaDtos);

    }

    @GetMapping(value = "/perfiles")
    public Flux<SoloPerfilImp> mostrarListaPerfiles(){
        return entrevistaPrueba.getPerfiles();
    }

    @GetMapping(value = "/entrevista_muestra_id")
    public Mono<IdEntrevistaDto> obtenerId(@RequestParam("perfil") String perfil){
        perfil = UriUtils.decode(perfil, StandardCharsets.UTF_8);
        return this.entrevistaPrueba.getIdEntrevista(perfil);
    }



}

