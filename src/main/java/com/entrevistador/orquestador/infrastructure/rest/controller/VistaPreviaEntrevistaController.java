package com.entrevistador.orquestador.infrastructure.rest.controller;

import com.entrevistador.orquestador.application.usescases.EntrevistaPrueba;
import com.entrevistador.orquestador.infrastructure.adapter.dto.IdEntrevistaDto;
import com.entrevistador.orquestador.infrastructure.adapter.dto.SoloPerfilDto;
import com.entrevistador.orquestador.infrastructure.adapter.mapper.EntrevistaPruebaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/v1/entrevistador/public")
@RequiredArgsConstructor
public class VistaPreviaEntrevistaController {

    private final EntrevistaPrueba entrevistaPrueba;
    private final EntrevistaPruebaMapper mapper;

    @GetMapping(value = "/perfiles")
    public Flux<SoloPerfilDto> mostrarListaPerfiles(){
        return entrevistaPrueba.getPerfiles()
                .map(this.mapper::mapSoloPerfilToSoloPerfilDto);
    }

    @GetMapping(value = "/entrevista_muestra_id")
    public Mono<IdEntrevistaDto> obtenerId(@RequestParam("perfil") String perfil){
        perfil = UriUtils.decode(perfil, StandardCharsets.UTF_8);
        return this.entrevistaPrueba.getIdEntrevista(perfil)
                .map(this.mapper::mapIdEntrevistaDto);
    }

}

