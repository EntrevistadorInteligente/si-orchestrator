package com.entrevistador.orquestador.infrastructure.rest.controller;

import com.entrevistador.orquestador.application.usescases.HojaDeVida;
import com.entrevistador.orquestador.application.usescases.SolicitudEntrevista;
import com.entrevistador.orquestador.dominio.model.dto.FormularioDto;
import com.entrevistador.orquestador.dominio.model.dto.HojaDeVidaDto;
import com.entrevistador.orquestador.dominio.model.dto.SolicitudHojaDeVidaDto;
import com.entrevistador.orquestador.infrastructure.adapter.dao.HojaDeVidaBdDao;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/hojadevida")
@RequiredArgsConstructor
public class HojaDeVidaController {

    private final HojaDeVida hojaDeVida;
    @GetMapping("/obtener/{username}")
    public Mono<HojaDeVidaDto> obtenerHojaDeVida(@RequestParam String username){
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
