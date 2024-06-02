package com.entrevistador.orquestador.infrastructure.rest.controller;

import com.entrevistador.orquestador.application.usescases.HojaDeVida;
import com.entrevistador.orquestador.dominio.model.dto.PerfilDto;
import com.entrevistador.orquestador.infrastructure.adapter.constants.ValidationsMessagesData;
import com.entrevistador.orquestador.infrastructure.adapter.util.SanitizeStringUtil;
import lombok.RequiredArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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
        return hojaDeVida.obtenerHojaDeVida(SanitizeStringUtil.sanitize(username));
    }

    @PutMapping("/{uuid}")
    public Mono<ResponseEntity<String>> actualizarDatosPerfil(
            @NotNull(message = ValidationsMessagesData.NOT_NULL_MESSAGE) @PathVariable String uuid,
            @Valid @RequestBody PerfilDto perfilDto) {
        return hojaDeVida.actualizarDatosPerfil(SanitizeStringUtil.sanitize(uuid),perfilDto)
                .then(Mono.just(ResponseEntity.status(HttpStatus.OK)
                        .body("Perfil actualizado con exito")));
    }

    @PostMapping("/cargas")
    public Mono<ResponseEntity<String>> cargarHojaDeVida(
            @RequestPart("file") Mono<FilePart> file,
            @NotNull(message = ValidationsMessagesData.NOT_NULL_MESSAGE) @RequestPart("username") String username) {
        return this.hojaDeVida.generarSolicitudHojaDeVida(file, SanitizeStringUtil.sanitize(username))
                .then(Mono.just(ResponseEntity.status(HttpStatus.CREATED)
                        .body("Archivo PDF cargado con exito")));
    }

}