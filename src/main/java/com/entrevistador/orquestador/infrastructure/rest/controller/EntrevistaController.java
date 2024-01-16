package com.entrevistador.orquestador.infrastructure.rest.controller;

import com.entrevistador.orquestador.application.usescases.SolicitudEntrevista;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/v1/entrevistador/orquestador")
@RequiredArgsConstructor
public class EntrevistaController {

    private final SolicitudEntrevista solicitudEntrevista;

    @PostMapping("/cv")
    public ResponseEntity<String> postCV(@RequestPart("file") MultipartFile file) {
        this.solicitudEntrevista.generarSolicitudEntrevista(file);
        return ResponseEntity.status(HttpStatus.CREATED).body("Archivo PDF cargado con exito");
    }

    @PostMapping("/oferta")
    public ResponseEntity<String> postOfertaLaboral(@RequestPart("file") MultipartFile file) {
        this.solicitudEntrevista.generarOfertaLaboral(file);
        return ResponseEntity.status(HttpStatus.CREATED).body("Archivo Oferta Laboral cargado con exito");
    }

}

