package com.entrevistador.orquestador.infrastructure.rest.controller;

import com.entrevistador.orquestador.application.usescases.SolicitudEntrevista;
import com.entrevistador.orquestador.dominio.model.dto.FormularioDto;
import com.entrevistador.orquestador.dominio.model.dto.ProcesoEntrevistaDto;
import com.entrevistador.orquestador.dominio.service.ActualizarEstadoProcesoEntrevistaService;
import com.entrevistador.orquestador.infrastructure.adapter.entity.ProcesoEntrevistaEntity;
import com.entrevistador.orquestador.infrastructure.adapter.repository.ProcesoEntrevistaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;


@RestController
@RequestMapping("/api/v1/entrevistador/orquestador")
@RequiredArgsConstructor
public class EntrevistaController {

    private final SolicitudEntrevista solicitudEntrevista;
    private final ActualizarEstadoProcesoEntrevistaService actualizarEstadoProcesoEntrevistaService;
    private final ProcesoEntrevistaRepository procesoEntrevistaRepository;

    @PostMapping(value = "/cv")
    public Mono<ResponseEntity<String>> crearSolicitudEntrevista(
            @RequestPart("file") Mono<FilePart> file,
            @RequestPart("formulario") FormularioDto formularioDto
    ) {
        return this.solicitudEntrevista.generarSolicitudEntrevista(file, formularioDto)
                .then(Mono.just(ResponseEntity.status(HttpStatus.CREATED)
                        .body("Archivo PDF cargado con exito")));
    }

    //TODO: Delete this method before do a push
    @PostMapping(value = "/testActualizacionEstadoEntrevista")
    public void test(@RequestBody ProcesoEntrevistaDto procesoEntrevistaDto) {
        actualizarEstadoProcesoEntrevistaService.ejecutar(procesoEntrevistaDto);
        return;
    }

    //TODO: Delete this method before do a push
    @GetMapping(value = "/testActualizacionEstadoEntrevista")
    public List<ProcesoEntrevistaEntity> test() {
        return this.procesoEntrevistaRepository.findAll();
    }

}

