package com.entrevistador.orquestador.infrastructure.rest.controller;

import com.entrevistador.orquestador.application.usescases.HojaDeVida;
import com.entrevistador.orquestador.dominio.model.dto.PerfilDto;
import lombok.RequiredArgsConstructor;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/hojas-de-vidas")
@RequiredArgsConstructor
@Validated
public class HojaDeVidaController {

    private final HojaDeVida hojaDeVida;

    @GetMapping("/{username}")
    public Mono<PerfilDto> obtenerHojaDeVida(@PathVariable String username) {
        return hojaDeVida.obtenerHojaDeVida(username);
    }

    @PutMapping("/{uuid}")
    public Mono<ResponseEntity<String>> actualizarDatosPerfil(
            @PathVariable String uuid,
            @Valid @RequestBody PerfilDto perfilDto) {
        return hojaDeVida.actualizarDatosPerfil(uuid,perfilDto)
                .then(Mono.just(ResponseEntity.status(HttpStatus.OK)
                        .body("Perfil actualizado con exito")));
    }

    @PostMapping("/cargas")
    public Mono<ResponseEntity<String>> cargarHojaDeVida(
            @RequestPart("file") Mono<FilePart> file,
            @RequestPart("username") String username) {
        return this.hojaDeVida.generarSolicitudHojaDeVida(file, username)
                .then(Mono.just(ResponseEntity.status(HttpStatus.CREATED)
                        .body("Archivo PDF cargado con exito")));
    }

}