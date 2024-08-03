package com.entrevistador.orquestador.infrastructure.rest.controller;

import com.entrevistador.orquestador.application.usescases.HojaDeVida;
import com.entrevistador.orquestador.infrastructure.adapter.constants.ValidationsMessagesData;
import com.entrevistador.orquestador.infrastructure.adapter.dto.GenericResponse;
import com.entrevistador.orquestador.infrastructure.adapter.dto.PerfilDto;
import com.entrevistador.orquestador.infrastructure.adapter.mapper.HojaDeVidaMapper;
import com.entrevistador.orquestador.infrastructure.adapter.util.SanitizeStringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/v1/hojas-de-vidas")
@RequiredArgsConstructor
@Validated
public class HojaDeVidaController {
    private final HojaDeVida       hojaDeVida;
    private final HojaDeVidaMapper mapper;

    @GetMapping("/{username}")
    public Mono<PerfilDto> obtenerHojaDeVida(@PathVariable String username) {
        return hojaDeVida.obtenerHojaDeVida(SanitizeStringUtil.sanitize(username))
                .map(this.mapper::mapHojaDeVidaToPerfilDto);
    }

    @PutMapping("/{uuid}")
    public Mono<ResponseEntity<GenericResponse>> actualizarDatosPerfil(
            @NotNull(message = ValidationsMessagesData.NOT_NULL_MESSAGE) @PathVariable String uuid,
            @Valid @RequestBody PerfilDto perfilDto) {
        return Mono.just(this.mapper.mapPerfilDtoToPerfil(perfilDto))
                .flatMap(perfil -> this.hojaDeVida.actualizarDatosPerfil(SanitizeStringUtil.sanitize(uuid), perfil))
                .then(Mono.just(ResponseEntity.status(HttpStatus.OK)
                        .body(GenericResponse.builder()
                                        .message("Perfil actualizado con exito")
                                        .build())));
    }

    @PostMapping("/cargas")
    public Mono<ResponseEntity<PerfilDto>> cargarHojaDeVida(
            @RequestPart("file") Mono<FilePart> file,
            @NotNull(message = ValidationsMessagesData.NOT_NULL_MESSAGE) @RequestPart("username") String username) {
        return this.hojaDeVida.generarSolicitudHojaDeVida(file, SanitizeStringUtil.sanitize(username))
                .map(this.mapper::mapHojaDeVidaToPerfilDto)
                .map(perfilDto -> ResponseEntity.status(HttpStatus.CREATED).body(perfilDto));
    }
}