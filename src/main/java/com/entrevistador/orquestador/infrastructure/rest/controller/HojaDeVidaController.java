package com.entrevistador.orquestador.infrastructure.rest.controller;

import com.entrevistador.orquestador.application.usescases.HojaDeVida;
import com.entrevistador.orquestador.dominio.model.dto.HojaDeVidaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/hojas-de-vidas")
@RequiredArgsConstructor
public class HojaDeVidaController {

    private final HojaDeVida hojaDeVida;
    @GetMapping("/{username}")
    public Mono<HojaDeVidaDto> obtenerHojaDeVida(@PathVariable String username){
        return hojaDeVida.obtenerHojaDeVida(username);
    }

    @PutMapping("/corregir-datos")
    public Mono<ResponseEntity<String>> corregirHojaDeVida(
            @RequestPart("hojaDeVida") HojaDeVidaDto hojaDeVidaDto
    ) {
        return hojaDeVida.guardarHojaDeVida(hojaDeVidaDto)
                .then(Mono.just(ResponseEntity.status(HttpStatus.OK)
                .body("Perfil guardado con exito")));
    }

    @PostMapping("/cargar")
    public Mono<ResponseEntity<String>> cargarHojaDeVida(
            @RequestPart("file") Mono<FilePart> file,
            @RequestPart("username") String username
    ) {
        return this.hojaDeVida.generarSolicitudHojaDeVida(file, username)
                .then(Mono.just(ResponseEntity.status(HttpStatus.CREATED)
                        .body("Archivo PDF cargado con exito")));
    }

}
